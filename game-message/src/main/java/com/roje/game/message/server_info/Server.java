// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: server.proto

package com.roje.game.message.server_info;

public final class Server {
  private Server() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ServInfo_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ServInfo_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ServRegRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ServRegRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ServRegResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ServRegResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ServInfoRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ServInfoRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ServInfoResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ServInfoResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014server.proto\"\212\001\n\010ServInfo\022\n\n\002id\030\001 \001(\005\022" +
      "\n\n\002ip\030\002 \001(\t\022\014\n\004port\030\003 \001(\005\022\014\n\004type\030\004 \001(\005\022" +
      "\016\n\006online\030\005 \001(\005\022\024\n\014maxUserCount\030\006 \001(\005\022\014\n" +
      "\004name\030\007 \001(\t\022\026\n\016requireVersion\030\010 \001(\005\"-\n\016S" +
      "ervRegRequest\022\033\n\010servInfo\030\001 \001(\0132\t.ServIn" +
      "fo\";\n\017ServRegResponse\022\017\n\007success\030\001 \001(\010\022\013" +
      "\n\003msg\030\002 \001(\t\022\n\n\002id\030\003 \001(\005\".\n\017ServInfoReque" +
      "st\022\033\n\010servInfo\030\001 \001(\0132\t.ServInfo\"0\n\020ServI" +
      "nfoResponse\022\017\n\007success\030\001 \001(\010\022\013\n\003msg\030\002 \001(" +
      "\tB%\n!com.roje.game.message.server_infoP\001" +
      "b\006proto3"
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
    internal_static_ServInfo_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ServInfo_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ServInfo_descriptor,
        new java.lang.String[] { "Id", "Ip", "Port", "Type", "Online", "MaxUserCount", "Name", "RequireVersion", });
    internal_static_ServRegRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_ServRegRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ServRegRequest_descriptor,
        new java.lang.String[] { "ServInfo", });
    internal_static_ServRegResponse_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_ServRegResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ServRegResponse_descriptor,
        new java.lang.String[] { "Success", "Msg", "Id", });
    internal_static_ServInfoRequest_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_ServInfoRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ServInfoRequest_descriptor,
        new java.lang.String[] { "ServInfo", });
    internal_static_ServInfoResponse_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_ServInfoResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ServInfoResponse_descriptor,
        new java.lang.String[] { "Success", "Msg", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
