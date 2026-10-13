
CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       user_status VARCHAR(30),
                       created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP WITH TIME ZONE,
                       roles VARCHAR(30)
);

CREATE TABLE auth_identity (
                               id UUID PRIMARY KEY,
                               user_id UUID NOT NULL,
                               provider VARCHAR(20) NOT NULL,
                               provider_id VARCHAR(255),
                               email VARCHAR(255) NOT NULL UNIQUE,
                               password_hash VARCHAR(255),
                               created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP WITH TIME ZONE,

                               CONSTRAINT fk_auth_identity_user
                                   FOREIGN KEY (user_id)
                                       REFERENCES users(id)
                                       ON DELETE CASCADE
);
