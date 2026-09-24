CREATE TABLE users(
                      id UUID PRIMARY KEY,
                      status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
                      created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                      updated_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE auth_identity (


                    id UUID PRIMARY KEY,
                    user_id UUID NOT NULL,
                    provider VARCHAR(25) NOT NULL,
                    provider_id VARCHAR(255) NOT NULL,
                    email VARCHAR(255),
                    password_hash VARCHAR(255),
                    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP WITH TIME ZONE,

           CONSTRAINT chk_auth_provider CHECK (provider IN ('DISCORD', 'GOOGLE', 'GITHUB', 'LOCAL')),

)