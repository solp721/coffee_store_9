package com.db.coffeestore9.group.controller;

import com.db.coffeestore9.group.common.RequestPairAmountPenalty;
import com.db.coffeestore9.group.common.RequestRechargeForm;
import com.db.coffeestore9.group.service.GroupCardService;
import com.db.coffeestore9.group.service.GroupUserService;
import com.db.coffeestore9.group.service.RechargeService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manageGroup/recharge")
@RequiredArgsConstructor
public class RechargeController {

  private final RechargeService rechargeService;
  private final GroupCardService groupCardService;
  private final GroupUserService groupUserService;

  @GetMapping("/form")
  public String rechargeForm(Model model, Principal principal) {
    model.addAttribute("groupUsers",
        groupCardService.getGroupUsers(groupUserService.getMyGroup(principal.getName())));
    return "/recharge/form";
  }

  @PostMapping("/request")
  public String processRecharge(Model model, Principal principal, @ModelAttribute
  RequestRechargeForm requestRechargeForm) {
    Long seq = rechargeService.requestRecharge(requestRechargeForm).getSeq();

    return "redirect:/manageGroup/recharge/pairAmount/" + seq;
  }

  /**
   * @param seq 생성된 Recharge의 seq
   * @return
   */
  @GetMapping("pairAmount/{seq}")
  public String pairAmountPenaltyCheckForm(Model model, Principal principal,
      @PathVariable("seq") Long seq) {
    model.addAttribute("rechargeUsers", rechargeService.checkRechargeUsersPairAmount(
        rechargeService.getRecharge(seq).getRechargeUsers()));

    return "/recharge/pairForm";
  }

  @PostMapping("/pairAmount/{seq}")
  public String pairAmountPenaltyCheck(Model model, Principal principal,
      @PathVariable("seq") Long seq, @ModelAttribute
  RequestPairAmountPenalty requestPairAmountPenalty) {

    rechargeService.requestPenalty(seq, requestPairAmountPenalty);

    return "redirect:/";
  }

  @PostMapping("/pay/{seq}")
  public String processingPayment(Model model, Principal principal, @PathVariable("seq") Long seq) {
    rechargeService.processRecharge(principal.getName(), seq);

    return "redirect:/";
  }
}
