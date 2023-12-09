package com.db.coffeestore9.group.domain;

import com.db.coffeestore9.global.config.BaseTimeEntity;
import com.db.coffeestore9.global.common.State;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recharge extends BaseTimeEntity {

  @Builder.Default
  private Timestamp finishedDate = null;

  @Builder.Default
  private Timestamp expirationDate = new Timestamp(System.currentTimeMillis());

  @Builder.Default
  private State state = State.ON_PROGRESS;

  @Column(nullable = false)
  private Integer rechargeAmount;

  private Integer pairAmount;

  @OneToMany(mappedBy = "recharge")
  private List<RechargeUser> rechargeUsers;

  public void changeState(State state) {
    this.state = state;
  }

}
