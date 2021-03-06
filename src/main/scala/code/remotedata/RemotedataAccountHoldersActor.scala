package code.remotedata

import akka.actor.Actor
import code.accountholder.{MapperAccountHolders, RemotedataAccountHoldersCaseClasses}
import code.actorsystem.ObpActorHelper
import code.model._
import code.util.Helper.MdcLoggable


class RemotedataAccountHoldersActor extends Actor with ObpActorHelper with MdcLoggable {

  val mapper = MapperAccountHolders
  val cc = RemotedataAccountHoldersCaseClasses

  def receive = {

    case cc.createAccountHolder(userId: Long, bankId: String, accountId: String, source: String) =>
      logger.debug("createAccountHolder(" + userId +", "+ bankId +", "+ accountId +", "+ source +")")
      sender ! extractResult(mapper.createAccountHolder(userId, bankId, accountId, source))

    case cc.getAccountHolders(bankId: BankId, accountId: AccountId) =>
      logger.debug("getAccountHolders(" + bankId +", "+ accountId +")")
      sender ! extractResult(mapper.getAccountHolders(bankId, accountId))

    case cc.bulkDeleteAllAccountHolders() =>
      logger.debug("bulkDeleteAllAccountHolders()")
      sender ! extractResult(mapper.bulkDeleteAllAccountHolders())

    case message => logger.warn("[AKKA ACTOR ERROR - REQUEST NOT RECOGNIZED] " + message)
  }
}

