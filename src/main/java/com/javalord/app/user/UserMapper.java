package com.javalord.app.user;

import com.javalord.app.user.request.ProfileUpdateRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public void mergerUserInfo(User user, ProfileUpdateRequest request) {
        if (StringUtils.isNoneBlank(request.getFirstName()) &&
            !user.getFirstName().equals(request.getFirstName())) {
            user.setFirstName(request.getFirstName());
        }

        if (StringUtils.isNoneBlank(request.getLastName()) &&
                !user.getLastName().equals(request.getLastName())) {
            user.setLastName(request.getLastName());
        }

        if (request.getDateOfBirth() != null &&
                !request.getDateOfBirth().equals(request.getDateOfBirth())) {
            user.setDateOfBirth(request.getDateOfBirth());
        }






    }
}
