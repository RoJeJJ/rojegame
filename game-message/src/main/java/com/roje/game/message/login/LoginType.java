// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: login.proto

package com.roje.game.message.login;

/**
 * Protobuf enum {@code LoginType}
 */
public enum LoginType
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>WeChat = 0;</code>
   */
  WeChat(0),
  /**
   * <code>Account = 1;</code>
   */
  Account(1),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>WeChat = 0;</code>
   */
  public static final int WeChat_VALUE = 0;
  /**
   * <code>Account = 1;</code>
   */
  public static final int Account_VALUE = 1;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static LoginType valueOf(int value) {
    return forNumber(value);
  }

  public static LoginType forNumber(int value) {
    switch (value) {
      case 0: return WeChat;
      case 1: return Account;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<LoginType>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      LoginType> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<LoginType>() {
          public LoginType findValueByNumber(int number) {
            return LoginType.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return com.roje.game.message.login.Login.getDescriptor().getEnumTypes().get(0);
  }

  private static final LoginType[] VALUES = values();

  public static LoginType valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private LoginType(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:LoginType)
}

