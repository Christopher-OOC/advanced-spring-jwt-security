package com.javalord.app.auth;

import com.javalord.app.auth.request.AuthenticationRequest;
import com.javalord.app.auth.request.RefreshRequest;
import com.javalord.app.auth.request.RegistrationRequest;
import com.javalord.app.auth.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);

    void register(RegistrationRequest request);

    AuthenticationResponse refreshToken(RefreshRequest request);


}
