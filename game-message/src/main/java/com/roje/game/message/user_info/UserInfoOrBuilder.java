// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: user_info.proto

package com.roje.game.message.user_info;

public interface UserInfoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:UserInfo)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   *用户id
   * </pre>
   *
   * <code>int64 id = 1;</code>
   */
  long getId();

  /**
   * <pre>
   *账户
   * </pre>
   *
   * <code>string account = 2;</code>
   */
  java.lang.String getAccount();
  /**
   * <pre>
   *账户
   * </pre>
   *
   * <code>string account = 2;</code>
   */
  com.google.protobuf.ByteString
      getAccountBytes();

  /**
   * <pre>
   *昵称
   * </pre>
   *
   * <code>string nickname = 3;</code>
   */
  java.lang.String getNickname();
  /**
   * <pre>
   *昵称
   * </pre>
   *
   * <code>string nickname = 3;</code>
   */
  com.google.protobuf.ByteString
      getNicknameBytes();

  /**
   * <pre>
   *头像
   * </pre>
   *
   * <code>string headimg = 4;</code>
   */
  java.lang.String getHeadimg();
  /**
   * <pre>
   *头像
   * </pre>
   *
   * <code>string headimg = 4;</code>
   */
  com.google.protobuf.ByteString
      getHeadimgBytes();

  /**
   * <pre>
   *性别
   * </pre>
   *
   * <code>int32 sex = 5;</code>
   */
  int getSex();

  /**
   * <pre>
   *金币
   * </pre>
   *
   * <code>int64 gold = 6;</code>
   */
  long getGold();

  /**
   * <pre>
   *token
   * </pre>
   *
   * <code>string gameToken = 7;</code>
   */
  java.lang.String getGameToken();
  /**
   * <pre>
   *token
   * </pre>
   *
   * <code>string gameToken = 7;</code>
   */
  com.google.protobuf.ByteString
      getGameTokenBytes();
}