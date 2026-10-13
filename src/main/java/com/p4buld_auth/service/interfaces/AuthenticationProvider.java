package com.p4buld_auth.service.interfaces;

import com.p4buld_auth.dto.AuthProviderDTO;
import com.p4buld_auth.dto.ExternalUser;
import com.p4buld_auth.model.provider.TypeProvider;

public interface AuthenticationProvider{
    TypeProvider getProvider();

    ExternalUser authenticateProvider(AuthProviderDTO providerDTO);




}
