package guiga.flex_users.flex_users_gamification;

import java.util.Map;

import org.hibernate.annotations.Type;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
public class PlayerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Builder.Default
    @Column
    private Integer points = 0;

    @Column(columnDefinition = "jsonb")
    @Type(JsonType.class)
    private Map<String, String> document;

}
