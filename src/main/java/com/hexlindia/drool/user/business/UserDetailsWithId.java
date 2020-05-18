package com.hexlindia.drool.user.business;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

public class UserDetailsWithId implements UserDetails, CredentialsContainer {
    private static final long serialVersionUID = 520L;
    private static final Log logger = LogFactory.getLog(com.hexlindia.drool.user.business.UserDetailsWithId.class);
    private final String userId;
    private final String emailId;
    private String password;
    private final String publicUsername;
    private final Set<GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    public UserDetailsWithId(String userId, String emailId, String password, String publicUsername, Collection<? extends GrantedAuthority> authorities) {
        this(userId, emailId, password, publicUsername, true, true, true, true, authorities);
    }

    public UserDetailsWithId(String userId, String emailId, String password, String publicUsername, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        if (emailId != null && !"".equals(emailId) && password != null) {
            this.userId = userId;
            this.emailId = emailId;
            this.password = password;
            this.publicUsername = publicUsername;
            this.enabled = enabled;
            this.accountNonExpired = accountNonExpired;
            this.credentialsNonExpired = credentialsNonExpired;
            this.accountNonLocked = accountNonLocked;
            this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
        } else {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return emailId;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPublicUsername() {
        return this.publicUsername;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    public void eraseCredentials() {
        this.password = null;
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet(new com.hexlindia.drool.user.business.UserDetailsWithId.AuthorityComparator());
        Iterator var2 = authorities.iterator();

        while (var2.hasNext()) {
            GrantedAuthority grantedAuthority = (GrantedAuthority) var2.next();
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    public boolean equals(Object rhs) {
        return rhs instanceof UserDetailsWithId && this.emailId.equals(((UserDetailsWithId) rhs).emailId);
    }

    public int hashCode() {
        return this.emailId.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("userId: ").append(this.userId).append("; ");
        sb.append("EmailId: ").append(this.emailId).append("; ");
        sb.append("Password: [PROTECTED]; ");
        sb.append("PublicUsername: ").append(this.publicUsername).append("; ");
        sb.append("Enabled: ").append(this.enabled).append("; ");
        sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
        sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
        sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");
        if (!this.authorities.isEmpty()) {
            sb.append("Granted Authorities: ");
            boolean first = true;
            Iterator var3 = this.authorities.iterator();

            while (var3.hasNext()) {
                GrantedAuthority auth = (GrantedAuthority) var3.next();
                if (!first) {
                    sb.append(",");
                }

                first = false;
                sb.append(auth);
            }
        } else {
            sb.append("Not granted any authorities");
        }

        return sb.toString();
    }

    public static com.hexlindia.drool.user.business.UserDetailsWithId.UserBuilder withUsername(String username) {
        return builder().username(username);
    }

    public static com.hexlindia.drool.user.business.UserDetailsWithId.UserBuilder builder() {
        return new com.hexlindia.drool.user.business.UserDetailsWithId.UserBuilder();
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static com.hexlindia.drool.user.business.UserDetailsWithId.UserBuilder withDefaultPasswordEncoder() {
        logger.warn("User.withDefaultPasswordEncoder() is considered unsafe for production and is only intended for sample applications.");
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        com.hexlindia.drool.user.business.UserDetailsWithId.UserBuilder var10000 = builder();
        encoder.getClass();
        return var10000.passwordEncoder(encoder::encode);
    }

    public static com.hexlindia.drool.user.business.UserDetailsWithId.UserBuilder withUserDetails(UserDetails userDetails) {
        return withUsername(userDetails.getUsername()).password(userDetails.getPassword()).accountExpired(!userDetails.isAccountNonExpired()).accountLocked(!userDetails.isAccountNonLocked()).authorities(userDetails.getAuthorities()).credentialsExpired(!userDetails.isCredentialsNonExpired()).disabled(!userDetails.isEnabled());
    }

    public static class UserBuilder {
        private String userId;
        private String emailId;
        private String password;
        private String publicUsername;
        private List<GrantedAuthority> authorities;
        private boolean accountExpired;
        private boolean accountLocked;
        private boolean credentialsExpired;
        private boolean disabled;
        private Function<String, String> passwordEncoder;

        private UserBuilder() {
            this.passwordEncoder = (password) -> {
                return password;
            };
        }

        public com.hexlindia.drool.user.business.UserDetailsWithId.UserBuilder username(String emailId) {
            Assert.notNull(emailId, "emailId cannot be null");
            this.emailId = emailId;
            return this;
        }

        public com.hexlindia.drool.user.business.UserDetailsWithId.UserBuilder password(String password) {
            Assert.notNull(password, "password cannot be null");
            this.password = password;
            return this;
        }

        public com.hexlindia.drool.user.business.UserDetailsWithId.UserBuilder passwordEncoder(Function<String, String> encoder) {
            Assert.notNull(encoder, "encoder cannot be null");
            this.passwordEncoder = encoder;
            return this;
        }

        public com.hexlindia.drool.user.business.UserDetailsWithId.UserBuilder publicUsername(String publicUsername) {
            Assert.notNull(emailId, "publicUsername cannot be null");
            this.publicUsername = publicUsername;
            return this;
        }

        public com.hexlindia.drool.user.business.UserDetailsWithId.UserBuilder roles(String... roles) {
            List<GrantedAuthority> authorities = new ArrayList(roles.length);
            String[] var3 = roles;
            int var4 = roles.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                String role = var3[var5];
                Assert.isTrue(!role.startsWith("ROLE_"), () -> {
                    return role + " cannot start with ROLE_ (it is automatically added)";
                });
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }

            return this.authorities(authorities);
        }

        public com.hexlindia.drool.user.business.UserDetailsWithId.UserBuilder authorities(GrantedAuthority... authorities) {
            return this.authorities(Arrays.asList(authorities));
        }

        public com.hexlindia.drool.user.business.UserDetailsWithId.UserBuilder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = new ArrayList(authorities);
            return this;
        }

        public com.hexlindia.drool.user.business.UserDetailsWithId.UserBuilder authorities(String... authorities) {
            return this.authorities(AuthorityUtils.createAuthorityList(authorities));
        }

        public com.hexlindia.drool.user.business.UserDetailsWithId.UserBuilder accountExpired(boolean accountExpired) {
            this.accountExpired = accountExpired;
            return this;
        }

        public com.hexlindia.drool.user.business.UserDetailsWithId.UserBuilder accountLocked(boolean accountLocked) {
            this.accountLocked = accountLocked;
            return this;
        }

        public com.hexlindia.drool.user.business.UserDetailsWithId.UserBuilder credentialsExpired(boolean credentialsExpired) {
            this.credentialsExpired = credentialsExpired;
            return this;
        }

        public com.hexlindia.drool.user.business.UserDetailsWithId.UserBuilder disabled(boolean disabled) {
            this.disabled = disabled;
            return this;
        }

        public UserDetails build() {
            String encodedPassword = this.passwordEncoder.apply(this.password);
            return new com.hexlindia.drool.user.business.UserDetailsWithId(this.userId, this.emailId, encodedPassword, this.publicUsername, !this.disabled, !this.accountExpired, !this.credentialsExpired, !this.accountLocked, this.authorities);
        }
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        private static final long serialVersionUID = 520L;

        private AuthorityComparator() {
        }

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            if (g2.getAuthority() == null) {
                return -1;
            } else {
                return g1.getAuthority() == null ? 1 : g1.getAuthority().compareTo(g2.getAuthority());
            }
        }
    }
}
