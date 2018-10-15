// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: server_info.proto

package com.roje.game.message.server_info;

public interface ServerInfoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:ServerInfo)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * 服务器ID
   * </pre>
   *
   * <code>int32 id = 1;</code>
   */
  int getId();

  /**
   * <pre>
   * 内网地址
   * </pre>
   *
   * <code>string ip = 2;</code>
   */
  java.lang.String getIp();
  /**
   * <pre>
   * 内网地址
   * </pre>
   *
   * <code>string ip = 2;</code>
   */
  com.google.protobuf.ByteString
      getIpBytes();

  /**
   * <pre>
   * 类型
   * </pre>
   *
   * <code>.ServerType type = 3;</code>
   */
  int getTypeValue();
  /**
   * <pre>
   * 类型
   * </pre>
   *
   * <code>.ServerType type = 3;</code>
   */
  com.roje.game.message.server_info.ServerType getType();

  /**
   * <pre>
   * 端口
   * </pre>
   *
   * <code>int32 innerPort = 4;</code>
   */
  int getInnerPort();

  /**
   * <pre>
   *状态 -1表示维护；0表示准备开启；1表示正常，2表示不显示，3表示内部开启
   * </pre>
   *
   * <code>.ServerStatus state = 5;</code>
   */
  int getStateValue();
  /**
   * <pre>
   *状态 -1表示维护；0表示准备开启；1表示正常，2表示不显示，3表示内部开启
   * </pre>
   *
   * <code>.ServerStatus state = 5;</code>
   */
  com.roje.game.message.server_info.ServerStatus getState();

  /**
   * <pre>
   * 在线人数
   * </pre>
   *
   * <code>int32 online = 6;</code>
   */
  int getOnline();

  /**
   * <pre>
   * 在线人数
   * </pre>
   *
   * <code>int32 maxUserCount = 7;</code>
   */
  int getMaxUserCount();

  /**
   * <pre>
   * http端口
   * </pre>
   *
   * <code>int32 httpport = 8;</code>
   */
  int getHttpport();

  /**
   * <pre>
   *名字
   * </pre>
   *
   * <code>string name = 9;</code>
   */
  java.lang.String getName();
  /**
   * <pre>
   *名字
   * </pre>
   *
   * <code>string name = 9;</code>
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <pre>
   * 外网地址
   * </pre>
   *
   * <code>string wwwip = 10;</code>
   */
  java.lang.String getWwwip();
  /**
   * <pre>
   * 外网地址
   * </pre>
   *
   * <code>string wwwip = 10;</code>
   */
  com.google.protobuf.ByteString
      getWwwipBytes();

  /**
   * <pre>
   *空闲内存
   * </pre>
   *
   * <code>int32 freeMemory = 11;</code>
   */
  int getFreeMemory();

  /**
   * <pre>
   *可用内存
   * </pre>
   *
   * <code>int32 totalMemory = 12;</code>
   */
  int getTotalMemory();

  /**
   * <pre>
   *版本号
   * </pre>
   *
   * <code>int32 version = 13;</code>
   */
  int getVersion();

  /**
   * <pre>
   *要求的客户端版本
   * </pre>
   *
   * <code>int32 requireClientVersion = 14;</code>
   */
  int getRequireClientVersion();

  /**
   * <code>int32 connectedCount = 15;</code>
   */
  int getConnectedCount();

  /**
   * <code>int32 userPort = 16;</code>
   */
  int getUserPort();
}
