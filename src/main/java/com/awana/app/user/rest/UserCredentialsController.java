package com.awana.app.user.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.awana.app.user.openapi.TagUser;

@RequestMapping("/api/user-app/credentials")
@RestController
@TagUser
public class UserCredentialsController {

}
