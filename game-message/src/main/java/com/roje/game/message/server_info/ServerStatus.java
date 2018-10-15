// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: server_info.proto

package com.roje.game.message.server_info;

/**
 * Protobuf enum {@code ServerStatus}
 */
public enum ServerStatus
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>Normal = 0;</code>
   */
  Normal(0),
  /**
   * <code>Maintain = 1;</code>
   */
  Maintain(1),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>Normal = 0;</code>
   */
  public static final int Normal_VALUE = 0;
  /**
   * <code>Maintain = 1;</code>
   */
  public static final int Maintain_VALUE = 1;


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
  public static ServerStatus valueOf(int value) {
    return forNumber(value);
  }

  public static ServerStatus forNumber(int value) {
    switch (value) {
      case 0: return Normal;
      case 1: return Maintain;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<ServerStatus>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      ServerStatus> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<ServerStatus>() {
          public ServerStatus findValueByNumber(int number) {
            return ServerStatus.forNumber(number);
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
    return com.roje.game.message.server_info.ServerInfoOuterClass.getDescriptor().getEnumTypes().get(1);
  }

  private static final ServerStatus[] VALUES = values();

  public static ServerStatus valueOf(
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

  private ServerStatus(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:ServerStatus)
}

