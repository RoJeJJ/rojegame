// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: golden-flaud.proto

package com.roje.game.message.gf;

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
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_BetNotice_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_BetNotice_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ActionRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ActionRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\022golden-flaud.proto\"\201\001\n\020GFCardRoomConfi" +
      "g\022\r\n\005round\030\001 \001(\005\022\017\n\007payment\030\002 \001(\005\022\016\n\006per" +
      "son\030\003 \001(\005\022\014\n\004base\030\004 \001(\005\022\013\n\003men\030\005 \001(\005\022\017\n\007" +
      "maxTurn\030\006 \001(\005\022\021\n\tswitch235\030\007 \001(\010\"%\n\tBetN" +
      "otice\022\n\n\002id\030\001 \001(\003\022\014\n\004time\030\002 \001(\003\"8\n\rActio" +
      "nRequest\022\016\n\006action\030\001 \001(\005\022\013\n\003add\030\002 \001(\005\022\n\n" +
      "\002id\030\003 \001(\003B\034\n\030com.roje.game.message.gfP\001b" +
      "\006proto3"
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
        new java.lang.String[] { "Round", "Payment", "Person", "Base", "Men", "MaxTurn", "Switch235", });
    internal_static_BetNotice_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_BetNotice_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_BetNotice_descriptor,
        new java.lang.String[] { "Id", "Time", });
    internal_static_ActionRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_ActionRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ActionRequest_descriptor,
        new java.lang.String[] { "Action", "Add", "Id", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}