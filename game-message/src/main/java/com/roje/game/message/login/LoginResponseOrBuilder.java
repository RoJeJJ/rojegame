// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: login.proto

package com.roje.game.message.login;

public interface LoginResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:LoginResponse)
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
   * <code>.UserInfo userInfo = 3;</code>
   */
  boolean hasUserInfo();
  /**
   * <code>.UserInfo userInfo = 3;</code>
   */
  com.roje.game.message.user_info.UserInfo getUserInfo();
  /**
   * <code>.UserInfo userInfo = 3;</code>
   */
  com.roje.game.message.user_info.UserInfoOrBuilder getUserInfoOrBuilder();
}
