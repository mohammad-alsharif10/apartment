package com.rent.apartment.database;


import com.rent.apartment.model.VerificationToken;

import java.util.Optional;

public interface VerificationTokenRepository extends BaseRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
}
