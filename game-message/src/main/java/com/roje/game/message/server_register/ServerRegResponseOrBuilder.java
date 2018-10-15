// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: server_register.proto

package com.roje.game.message.server_register;

public interface ServerRegResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:ServerRegResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>bool success = 1;</code>
   */
  boolean getSuccess();

  /**
   * <code>string msg = 2;</code>
   */
  java.lang.String getMsg();
  /**
   * <code>string msg = 2;</code>
   */
  com.google.protobuf.ByteString
      getMsgBytes();

  /**
   * <code>int32 id = 3;</code>
   */
  int getId();

  /**
   * <code>repeated .Connection gateConns = 4;</code>
   */
  java.util.List<com.roje.game.message.server_register.Connection> 
      getGateConnsList();
  /**
   * <code>repeated .Connection gateConns = 4;</code>
   */
  com.roje.game.message.server_register.Connection getGateConns(int index);
  /**
   * <code>repeated .Connection gateConns = 4;</code>
   */
  int getGateConnsCount();
  /**
   * <code>repeated .Connection gateConns = 4;</code>
   */
  java.util.List<? extends com.roje.game.message.server_register.ConnectionOrBuilder> 
      getGateConnsOrBuilderList();
  /**
   * <code>repeated .Connection gateConns = 4;</code>
   */
  com.roje.game.message.server_register.ConnectionOrBuilder getGateConnsOrBuilder(
      int index);

  /**
   * <code>.ServerType type = 5;</code>
   */
  int getTypeValue();
  /**
   * <code>.ServerType type = 5;</code>
   */
  com.roje.game.message.server_info.ServerType getType();
}