package com.example.movieapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_tokens_id_seq")
    @SequenceGenerator(name = "refresh_tokens_id_seq", sequenceName = "refresh_tokens_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "jti", nullable = false, unique = true, length = 36)
    private String jti;

    @NotNull
    @Column(name = "token_hash", nullable = false, length = 255)
    private String tokenHash;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Builder.Default
    @Column(name = "revoked", nullable = false)
    private Boolean revoked = false;

    @Column(name = "revoked_at")
    private Instant revokedAt;

    public boolean isValid() {
        return !Boolean.TRUE.equals(revoked) && expiresAt.isAfter(Instant.now());
    }
}
