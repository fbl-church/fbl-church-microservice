package com.fbl.app.vbs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.fbl.app.vbs.client.domain.VBSAttendanceRecord;
import com.fbl.app.vbs.client.domain.VBSChildPointsCard;
import com.fbl.app.vbs.client.domain.VBSPointDivision;
import com.fbl.app.vbs.client.domain.VBSThemeGroup;
import com.fbl.app.vbs.dao.VBSReportsDAO;
import com.fbl.common.enums.ChurchGroup;
import com.fbl.common.freemarker.FreeMarkerUtility;
import com.fbl.common.page.Page;

/**
 * VBS Report Service
 * 
 * @author Sam Butler
 * @since Jun 21, 2024
 */
@Service
public class VBSReportsService {
    private static final int EVERY_DAY_BONUS_POINTS = 500;

    @Autowired
    private VBSReportsDAO vbsReportsDao;

    @Autowired
    private VBSAttendanceService vbsAttendanceService;

    @Autowired
    private VBSPointDivisionService vbsPointDivisionService;

    @Autowired
    private FreeMarkerUtility freeMarkerUtility;

    /**
     * Gets statistics of children for vbs for the given theme id
     * 
     * @return a map with data
     */
    public Map<String, Integer> getCurrentlyRegisterVBSChildren() {
        Map<String, Integer> data = new HashMap<>();
        data.put("registeredChildren", vbsReportsDao.getCountOfRegisteredVBSChildren());
        return data;
    }

    /**
     * Returns a byte array of the children points pdf
     * 
     * @return a byte array of the children points pdf
     */
    public byte[] getChildrenPointsPDF(int themeId, ChurchGroup group) throws Exception {
        List<VBSAttendanceRecord> recs = vbsAttendanceService.getVBSAttendanceRecordsByThemeId(themeId);
        List<VBSPointDivision> pointDivisions = vbsPointDivisionService.getVBSPointDivisionsByThemeId(themeId)
                .getList();

        Set<Integer> recordIds = recs.stream().map(VBSAttendanceRecord::getId).collect(Collectors.toSet());
        List<VBSChildPointsCard> pointCards = vbsReportsDao.getVBSChildPointCards(recordIds, group);

        String translatedGroup = translateGroup(group);
        pointCards.forEach(p -> {
            if (p.getDaysAttended() == 5) {
                p.setTotalPoints(p.getTotalPoints() + EVERY_DAY_BONUS_POINTS);
            }
            p.setTotalPoints(p.getTotalPoints() + p.getOfferingPoints());
            p.setCardColor(determineCardColor(p.getTotalPoints(), pointDivisions));
            p.setGroup(translatedGroup);
        });

        String html = freeMarkerUtility.generateMessage("VBSChildrenPoints.html",
                Map.of("list", pointCards, "group", translatedGroup));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        return outputStream.toByteArray();
    }

    /**
     * Get the snack details for the VBS
     * 
     * @param id The id of the attendance record to get for the snack details
     * @return a page of VBS Theme Groups
     */
    public Page<VBSThemeGroup> getSnackDetails(int id) {
        return vbsReportsDao.getSnackDetails(id);
    }

    /**
     * Translates the group to a string
     * 
     * @param group The group to translate
     * @return the translated group
     */
    private String translateGroup(ChurchGroup group) {
        switch (group) {
            case VBS_PRE_PRIMARY:
                return "VBS Pre-Primary (4's and 5's)";
            case VBS_PRIMARY:
                return "VBS Primary (1st and 2nd)";
            case VBS_MIDDLER:
                return "VBS Middler (3rd and 4th)";
            case VBS_JUNIOR:
                return "VBS Junior (5th and 6th)";

            default:
                return "Unknown";
        }
    }

    private String determineCardColor(int totalPoints, List<VBSPointDivision> pointDivisions) {
        return pointDivisions.stream()
                .filter(division -> totalPoints >= division.getMin() && totalPoints <= division.getMax())
                .findFirst().map(pd -> pd.getColor()).orElse("rgb(0, 0, 0)");
    }
}
