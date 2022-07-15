package johan.spekman.novibeie.module_promotion.model;

import javax.persistence.*;

@Entity
@Table(name = "sales_voucher_promotions")
public class Voucher extends Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entity_id")
    private Long entityId;
    private String voucherCode;

    public Voucher() {
        super();
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }
}
