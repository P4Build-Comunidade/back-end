ALTER TABLE auth_identity
    ADD CONSTRAINT fk_auth_identity_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE;

ALTER TABLE auth_identity
    ADD CONSTRAINT uk_provider_provider_id
        UNIQUE (provider, provider_id);