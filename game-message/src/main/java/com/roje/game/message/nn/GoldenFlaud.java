// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: golden-flaud.proto

package com.roje.game.message.nn;

public final class GoldenFlaud {
  private GoldenFlaud() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_GFCardRoomConfig_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_GFCardRoomConfig_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\022golden-flaud.proto\"B\n\020GFCardRoomConfig" +
      "\022\r\n\005round\030\001 \001(\005\022\017\n\007payment\030\002 \001(\005\022\016\n\006pers" +
      "on\030\003 \001(\005B\034\n\030com.roje.game.message.nnP\001b\006" +
      "proto3"
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
    internal_static_GFCardRoomConfig_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_GFCardRoomConfig_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_GFCardRoomConfig_descriptor,
        new java.lang.String[] { "Round", "Payment", "Person", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}