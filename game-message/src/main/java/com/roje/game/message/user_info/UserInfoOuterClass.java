// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: user_info.proto

package com.roje.game.message.user_info;

public final class UserInfoOuterClass {
  private UserInfoOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_UserInfo_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_UserInfo_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\017user_info.proto\"x\n\010UserInfo\022\n\n\002id\030\001 \001(" +
      "\003\022\017\n\007account\030\002 \001(\t\022\020\n\010nickname\030\003 \001(\t\022\017\n\007" +
      "headimg\030\004 \001(\t\022\013\n\003sex\030\005 \001(\005\022\014\n\004gold\030\006 \001(\003" +
      "\022\021\n\tgameToken\030\007 \001(\tB#\n\037com.roje.game.mes" +
      "sage.user_infoP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_UserInfo_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_UserInfo_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_UserInfo_descriptor,
        new java.lang.String[] { "Id", "Account", "Nickname", "Headimg", "Sex", "Gold", "GameToken", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}