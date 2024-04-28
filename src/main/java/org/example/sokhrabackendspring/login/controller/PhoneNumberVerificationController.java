package org.example.sokhrabackendspring.login.controller;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import org.example.sokhrabackendspring.login.model.PhoneNumber;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhoneNumberVerificationController {
  final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();


  @GetMapping(path = "/verifyPhoneNumber/{countryCode}/{nationalNumber}", produces = "application/json")
  ResponseEntity<?> verifyPhoneNumber(@PathVariable("countryCode") int countryCode, @PathVariable("nationalNumber") long nationalNumber) throws JSONException {
    PhoneNumber phoneNumber = new PhoneNumber();
    phoneNumber.setCountryCode(countryCode);
    phoneNumber.setNationalNumber(nationalNumber);
    System.out.println(countryCode);
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(new JSONObject().put("isValid", phoneNumberUtil.isValidNumber(phoneNumber)).toString());
  }

}
