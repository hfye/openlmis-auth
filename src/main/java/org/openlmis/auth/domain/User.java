package org.openlmis.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "auth_users")
@JsonIgnoreProperties(value = { "authorities" }, ignoreUnknown = true)
public class User extends BaseEntity implements UserDetails {

  @Getter
  @Setter
  @Type(type = "pg-uuid")
  @Column(nullable = false, unique = true)
  private UUID referenceDataUserId;

  @Getter
  @Setter
  @Column(nullable = false, unique = true)
  private String username;

  @Getter
  @Setter
  @Column
  private String password;

  @Getter
  @Setter
  @Column(nullable = false, unique = true)
  private String email;

  @Getter
  @Setter
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private UserRole role;

  @Getter
  @Setter
  @Column
  private Boolean enabled;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return AuthorityUtils.createAuthorityList(this.getRole().name(), UserRole.USER.name());
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.getEnabled();
  }
}
