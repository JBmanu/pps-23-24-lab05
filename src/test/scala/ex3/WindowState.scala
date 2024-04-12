package ex3

import ex3.States.State
import util.Streams
import util.Streams.*


trait WindowState:
  type Window

  def initialWindow: Window

  def show(): State[Window, Unit]

  def exec(cmd: => Unit): State[Window, Unit]

  def eventStream(): State[Window, Stream[String]]

//object WindowStateImpl extends WindowState:
//
//  import SwingFunctionalFacade.*
//
//  type Window = Frame
//
//  def initialWindow: Window = createFrame
//
//  def show(): State[Window, Unit] =
//    State(w => (w.show, {}))
//
//  def exec(cmd: => Unit): State[Window, Unit] =
//    State(w => (w, cmd))
//
//  def eventStream(): State[Window, Stream[String]] =
//    State(w => (w, Stream.generate(() => w.events().get)))
//
