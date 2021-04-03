package com.makeathon.sehad.models.db;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Authority", schema = "public")
public class Authority {

    @Id
    @Column(name = "Authority_id")
    @SequenceGenerator(name = "SEQ", sequenceName = "AUTHORITY_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ")
    private Long authorityId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Authentication.class)
    @JoinColumn(name = "user_id_fk", referencedColumnName = "user_id", nullable = false)
    private Authentication authentication;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "start_date")
    private Date startDate = new Date();

    @Column(name = "end_date")
    private Date endDate;

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    public Authentication getUserId() {
        return authentication;
    }

    public void setUserId(Authentication userId) {
        this.authentication = userId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Authority{" +
//                "authentication='" + authentication.toString() + '\'' +
                ", roleName='" + roleName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
