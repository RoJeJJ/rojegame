// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: inner_message.proto

package com.roje.game.message.inner_message;

public interface InnerMessageOrBuilder extends
    // @@protoc_insertion_point(interface_extends:InnerMessage)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 uid = 1;</code>
   */
  long getUid();

  /**
   * <code>.google.protobuf.Any data = 2;</code>
   */
  boolean hasData();
  /**
   * <code>.google.protobuf.Any data = 2;</code>
   */
  com.google.protobuf.Any getData();
  /**
   * <code>.google.protobuf.Any data = 2;</code>
   */
  com.google.protobuf.AnyOrBuilder getDataOrBuilder();
}