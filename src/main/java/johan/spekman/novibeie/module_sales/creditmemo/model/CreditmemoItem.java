package johan.spekman.novibeie.module_sales.creditmemo.model;

import johan.spekman.novibeie.module_sales.SalesResourceItem;

import javax.persistence.*;

@Entity
@Table(name = "sales_creditmemo_items")
public class CreditmemoItem extends SalesResourceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityId;
    @ManyToOne
    @JoinColumn(name = "creditmemo_entity_id")
    private Creditmemo creditmemo;
}
