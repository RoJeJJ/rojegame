// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: server.proto

package com.roje.game.message.server_info;

public interface ServInfoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:ServInfo)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int32 id = 1;</code>
   */
  int getId();

  /**
   * <pre>
   * ip地址
   * </pre>
   *
   * <code>string ip = 2;</code>
   */
  java.lang.String getIp();
  /**
   * <pre>
   * ip地址
   * </pre>
   *
   * <code>string ip = 2;</code>
   */
  com.google.protobuf.ByteString
      getIpBytes();

  /**
   * <code>int32 port = 3;</code>
   */
  int getPort();

  /**
   * <code>int32 gameId = 4;</code>
   */
  int getGameId();

  /**
   * <pre>
   * 在线人数
   * </pre>
   *
   * <code>int32 online = 5;</code>
   */
  int getOnline();

  /**
   * <pre>
   * 最大在线人数
   * </pre>
   *
   * <code>int32 maxUserCount = 6;</code>
   */
  int getMaxUserCount();

  /**
   * <pre>
   *名字
   * </pre>
   *
   * <code>string name = 7;</code>
   */
  java.lang.String getName();
  /**
   * <pre>
   *名字
   * </pre>
   *
   * <code>string name = 7;</code>
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <code>int32 requireVersion = 8;</code>
   */
  int getRequireVersion();
}
