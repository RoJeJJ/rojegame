// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: error_message.proto

package com.roje.game.message.error;

public final class ErrorMessageOuterClass {
  private ErrorMessageOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ErrorMessage_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ErrorMessage_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\023error_message.proto\";\n\014ErrorMessage\022\033\n" +
      "\007errCode\030\001 \001(\0162\n.ErrorCode\022\016\n\006errMsg\030\002 \001" +
      "(\t*s\n\tErrorCode\022\017\n\013HallNotFind\020\000\022\017\n\013Gate" +
      "NotFind\020\001\022\016\n\nNotLoginOn\020\002\022\020\n\014ConnectRese" +
      "t\020\003\022\017\n\013RepeatedReq\020\004\022\021\n\rAlreadyLogged\020\005B" +
      "\037\n\033com.roje.game.message.errorP\001b\006proto3"
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
    internal_static_ErrorMessage_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ErrorMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ErrorMessage_descriptor,
        new java.lang.String[] { "ErrCode", "ErrMsg", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
