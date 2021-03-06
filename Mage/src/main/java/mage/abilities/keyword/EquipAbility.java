package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.EquipEffect;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class EquipAbility extends ActivatedAbilityImpl {
  
    public EquipAbility(int cost) {
        this(Outcome.AddAbility, new GenericManaCost(cost));
    }

    public EquipAbility(Outcome outcome, Cost cost) {
        this(outcome, cost, new TargetControlledCreaturePermanent());
    }

    public EquipAbility(Outcome outcome, Cost cost, Target target) {
        super(Zone.BATTLEFIELD, new EquipEffect(outcome), cost);
        this.addTarget(target);
        this.timing = TimingRule.SORCERY;        
    }

    public EquipAbility(final EquipAbility ability) {
        super(ability);
    }

    @Override
    public EquipAbility copy() {
        return new EquipAbility(this);
    }

    @Override
    public String getRule() {
        String targetText = getTargets().get(0) != null ? getTargets().get(0).getFilter().getMessage() : "creature";
        
        return "Equip "  
                + (targetText.equals("creature you control") ? "" : targetText + " ")
                + costs.getText() 
                + manaCosts.getText() 
                + " <i>(" + manaCosts.getText() + ": Attach to target " + targetText + " you control. Equip only as a sorcery. This card enters the battlefield unattached and stays on the battlefield if the creature leaves.)</i>";
    }

}
