package com.makeathon.sehad.mapper;

import com.makeathon.sehad.models.db.Authentication;
import com.makeathon.sehad.models.db.Authority;
import com.makeathon.sehad.models.payload.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RequestToAuthModel {
    public void mapToAuthModel(Authentication auth, Profile request) {
        auth.setUserId(request.getUserId());
        auth.setEmail(request.getEmail());
        auth.setPassword(request.getPassword());
        auth.setDisabled(Boolean.FALSE);
        auth.setDisabledDate(null);

        List<Authority> authorityList = new ArrayList<>();
        String role = request.getDoctor() ? "ROLE_DOCTOR" : "ROLE_PATIENT";
        authorityList.add(createRole(auth, role));

        auth.setAuthorityList(authorityList);
    }

    private Authority createRole(Authentication auth, String role) {
        Authority authority = new Authority();
        authority.setAuthorityId(0L);
        authority.setRoleName(role);
        authority.setStartDate(new Date());
        authority.setEndDate(null);
        authority.setUserId(auth);
        return authority;
    }
}
