package com.mygdx.game.gui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.mygdx.game.ScreenResources
import priv.sp.{Creature, Spell, House, Card}

class CardActors(val card: Card, house : House, resources : ScreenResources) {
  val houseFolder = if (card.houseIndex < 4) "base" else house.name.toLowerCase.replaceAll(" ", "")
  val path = "cards/" + houseFolder + "/" + card.name
  val sprite = resources.atlas createSprite path
  if (card.isSpell) sprite.setPosition(-1, -1)
  else              sprite.setPosition(3, 14)

  val borderName = if (card.isSpell) "rakaSpell" else "raka"
  val borderTex = resources.atlas findRegion ("combat/"+borderName)

  val costLabel = new Label(card.cost.toString, resources.skin)
  costLabel setColor Color.BLUE
  val labels :List[Label] = card match {
    case spell: Spell ⇒
      costLabel.setPosition(65, 75)
      List(costLabel)
    case creature: Creature ⇒
      costLabel.setPosition(65, 80)
      val attackLabel = new Label(creature.attack.base.map(_.toString) getOrElse "?", resources.skin)
      val lifeLabel = new Label(creature.life.toString, resources.skin)
      attackLabel setColor Color.RED
      attackLabel.setPosition(2, 2)
      lifeLabel setColor Color.GREEN
      lifeLabel.setPosition(65, 2)
      List(costLabel, attackLabel, lifeLabel)
  }

  val imageActor = new Actor {
    override def draw(batch : Batch, parentAlpha : Float): Unit = {
      sprite draw batch
      batch.draw(borderTex, getX, getY)
    }
  }
  val actors = imageActor :: labels

  assert(sprite != null, "sprite not defined " + path)

}