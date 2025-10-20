package com.hss.hss_backend.service;

import com.hss.hss_backend.entity.Role;
import com.hss.hss_backend.entity.Staff;
import com.hss.hss_backend.entity.StaffRole;
import com.hss.hss_backend.entity.UserAccount;
import com.hss.hss_backend.repository.RoleRepository;
import com.hss.hss_backend.repository.StaffRepository;
import com.hss.hss_backend.repository.StaffRoleRepository;
import com.hss.hss_backend.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StaffRoleRepository staffRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Otomatik olarak test kullanıcısı oluşturur
     */
    public UserAccount createTestUser(String username, String email, String fullName, String roleName) {
        // Önce Staff oluştur
        Staff staff = Staff.builder()
                .fullName(fullName)
                .email(email)
                .phone("+90 555 123 4567")
                .hireDate(LocalDate.now())
                .active(true)
                .position("Test Position")
                .department("IT")
                .salary(java.math.BigDecimal.valueOf(50000))
                .address("Test Address")
                .emergencyContactName("Emergency Contact")
                .emergencyContactPhone("+90 555 987 6543")
                .build();

        staff = staffRepository.save(staff);

        // Role'ü bul veya oluştur
        Role role = findOrCreateRole(roleName);

        // UserAccount oluştur
        UserAccount userAccount = UserAccount.builder()
                .staff(staff)
                .username(username)
                .passwordHash(passwordEncoder.encode("password123")) // Varsayılan şifre
                .email(email)
                .isActive(true)
                .loginAttempts(0)
                .build();

        userAccount = userAccountRepository.save(userAccount);

        // StaffRole oluştur ve kaydet
        StaffRole staffRole = StaffRole.builder()
                .staff(staff)
                .role(role)
                .assignedDate(LocalDate.now())
                .assignedBy("SYSTEM")
                .build();

        staffRoleRepository.save(staffRole);

        return userAccount;
    }

    /**
     * Role'ü bulur, yoksa oluşturur
     */
    private Role findOrCreateRole(String roleName) {
        Optional<Role> existingRole = roleRepository.findByName(roleName);
        if (existingRole.isPresent()) {
            return existingRole.get();
        }

        // Yeni role oluştur
        Role newRole = Role.builder()
                .name(roleName)
                .description("Otomatik oluşturulan " + roleName + " rolü")
                .isSystemRole(false)
                .permissions(getDefaultPermissionsForRole(roleName))
                .build();

        return roleRepository.save(newRole);
    }

    /**
     * Role'e göre varsayılan izinleri döndürür
     */
    private List<String> getDefaultPermissionsForRole(String roleName) {
        switch (roleName.toUpperCase()) {
            case "ADMIN":
                return Arrays.asList(
                    "READ_ANIMALS", "WRITE_ANIMALS", "DELETE_ANIMALS",
                    "READ_APPOINTMENTS", "WRITE_APPOINTMENTS", "DELETE_APPOINTMENTS",
                    "READ_MEDICAL_HISTORY", "WRITE_MEDICAL_HISTORY", "DELETE_MEDICAL_HISTORY",
                    "READ_LAB_TESTS", "WRITE_LAB_TESTS", "DELETE_LAB_TESTS",
                    "READ_PRESCRIPTIONS", "WRITE_PRESCRIPTIONS", "DELETE_PRESCRIPTIONS",
                    "READ_VACCINATIONS", "WRITE_VACCINATIONS", "DELETE_VACCINATIONS",
                    "READ_INVOICES", "WRITE_INVOICES", "DELETE_INVOICES",
                    "READ_INVENTORY", "WRITE_INVENTORY", "DELETE_INVENTORY",
                    "READ_FILES", "WRITE_FILES", "DELETE_FILES",
                    "READ_DASHBOARD", "READ_STAFF", "WRITE_STAFF", "DELETE_STAFF"
                );
            case "VETERINARIAN":
                return Arrays.asList(
                    "READ_ANIMALS", "WRITE_ANIMALS",
                    "READ_APPOINTMENTS", "WRITE_APPOINTMENTS",
                    "READ_MEDICAL_HISTORY", "WRITE_MEDICAL_HISTORY",
                    "READ_LAB_TESTS", "WRITE_LAB_TESTS",
                    "READ_PRESCRIPTIONS", "WRITE_PRESCRIPTIONS",
                    "READ_VACCINATIONS", "WRITE_VACCINATIONS",
                    "READ_FILES", "WRITE_FILES",
                    "READ_DASHBOARD"
                );
            case "STAFF":
                return Arrays.asList(
                    "READ_ANIMALS", "WRITE_ANIMALS",
                    "READ_APPOINTMENTS", "WRITE_APPOINTMENTS",
                    "READ_LAB_TESTS", "WRITE_LAB_TESTS",
                    "READ_VACCINATIONS", "WRITE_VACCINATIONS",
                    "READ_INVENTORY", "WRITE_INVENTORY",
                    "READ_FILES", "WRITE_FILES",
                    "READ_DASHBOARD"
                );
            case "RECEPTIONIST":
                return Arrays.asList(
                    "READ_ANIMALS",
                    "READ_APPOINTMENTS", "WRITE_APPOINTMENTS",
                    "READ_INVOICES", "WRITE_INVOICES",
                    "READ_DASHBOARD"
                );
            default:
                return Arrays.asList("READ_DASHBOARD");
        }
    }

    /**
     * Kullanıcı adına göre kullanıcıyı bulur
     */
    public Optional<UserAccount> findByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }

    /**
     * Email'e göre kullanıcıyı bulur
     */
    public Optional<UserAccount> findByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }

    /**
     * Kullanıcının rollerini döndürür
     */
    public List<String> getUserRoles(String username) {
        Optional<UserAccount> userAccount = userAccountRepository.findByUsername(username);
        if (userAccount.isPresent()) {
            List<StaffRole> staffRoles = staffRoleRepository.findByStaffStaffId(userAccount.get().getStaff().getStaffId());
            return staffRoles.stream()
                    .map(staffRole -> staffRole.getRole().getName())
                    .toList();
        }
        return Arrays.asList();
    }
}
