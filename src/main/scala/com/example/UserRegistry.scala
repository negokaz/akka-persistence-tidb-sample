package com.example

//#user-registry-actor
import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.persistence.typed.PersistenceId
import akka.persistence.typed.scaladsl.{ Effect, EventSourcedBehavior }

import scala.collection.immutable

//#user-case-classes
final case class User(name: String, age: Int, countryOfResidence: String)
final case class Users(users: immutable.Seq[User])
//#user-case-classes

object UserRegistry {
  // actor protocol
  sealed trait Command
  final case class GetUsers(replyTo: ActorRef[Users])                           extends Command
  final case class CreateUser(user: User, replyTo: ActorRef[ActionPerformed])   extends Command
  final case class GetUser(name: String, replyTo: ActorRef[GetUserResponse])    extends Command
  final case class DeleteUser(name: String, replyTo: ActorRef[ActionPerformed]) extends Command

  final case class GetUserResponse(maybeUser: Option[User])
  final case class ActionPerformed(description: String)

  sealed trait Event                         extends AppSerializable
  final case class UserCreated(user: User)   extends Event
  final case class UserDeleted(name: String) extends Event

  def apply(): Behavior[Command] =
    EventSourcedBehavior[Command, Event, Set[User]](
      persistenceId = PersistenceId.ofUniqueId("user_registry"),
      emptyState = Set.empty,
      commandHandler = (users, cmd) =>
        cmd match {
          case GetUsers(replyTo) =>
            Effect.reply(replyTo)(Users(users.toSeq))
          case CreateUser(user, replyTo) =>
            Effect
              .persist(UserCreated(user))
              .thenReply(replyTo)(_ => ActionPerformed(s"User ${user.name} created."))
          case GetUser(name, replyTo) =>
            Effect.reply(replyTo)(GetUserResponse(users.find(_.name == name)))
          case DeleteUser(name, replyTo) =>
            Effect
              .persist(UserDeleted(name))
              .thenReply(replyTo)(_ => ActionPerformed(s"User $name deleted."))
        },
      eventHandler = (users, evt) =>
        evt match {
          case UserCreated(user) =>
            users + user
          case UserDeleted(name) =>
            users.filter(_.name == name)
        },
    )
}
//#user-registry-actor
