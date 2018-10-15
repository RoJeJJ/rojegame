// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: server_register.proto

package com.roje.game.message.server_register;

/**
 * Protobuf type {@code ServerRegRequest}
 */
public  final class ServerRegRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:ServerRegRequest)
    ServerRegRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ServerRegRequest.newBuilder() to construct.
  private ServerRegRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ServerRegRequest() {
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ServerRegRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            com.roje.game.message.server_info.ServerInfo.Builder subBuilder = null;
            if (serverInfo_ != null) {
              subBuilder = serverInfo_.toBuilder();
            }
            serverInfo_ = input.readMessage(com.roje.game.message.server_info.ServerInfo.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(serverInfo_);
              serverInfo_ = subBuilder.buildPartial();
            }

            break;
          }
          default: {
            if (!parseUnknownFieldProto3(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.roje.game.message.server_register.ServerRegister.internal_static_ServerRegRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.roje.game.message.server_register.ServerRegister.internal_static_ServerRegRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.roje.game.message.server_register.ServerRegRequest.class, com.roje.game.message.server_register.ServerRegRequest.Builder.class);
  }

  public static final int SERVERINFO_FIELD_NUMBER = 1;
  private com.roje.game.message.server_info.ServerInfo serverInfo_;
  /**
   * <code>.ServerInfo serverInfo = 1;</code>
   */
  public boolean hasServerInfo() {
    return serverInfo_ != null;
  }
  /**
   * <code>.ServerInfo serverInfo = 1;</code>
   */
  public com.roje.game.message.server_info.ServerInfo getServerInfo() {
    return serverInfo_ == null ? com.roje.game.message.server_info.ServerInfo.getDefaultInstance() : serverInfo_;
  }
  /**
   * <code>.ServerInfo serverInfo = 1;</code>
   */
  public com.roje.game.message.server_info.ServerInfoOrBuilder getServerInfoOrBuilder() {
    return getServerInfo();
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (serverInfo_ != null) {
      output.writeMessage(1, getServerInfo());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (serverInfo_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getServerInfo());
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.roje.game.message.server_register.ServerRegRequest)) {
      return super.equals(obj);
    }
    com.roje.game.message.server_register.ServerRegRequest other = (com.roje.game.message.server_register.ServerRegRequest) obj;

    boolean result = true;
    result = result && (hasServerInfo() == other.hasServerInfo());
    if (hasServerInfo()) {
      result = result && getServerInfo()
          .equals(other.getServerInfo());
    }
    result = result && unknownFields.equals(other.unknownFields);
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasServerInfo()) {
      hash = (37 * hash) + SERVERINFO_FIELD_NUMBER;
      hash = (53 * hash) + getServerInfo().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.roje.game.message.server_register.ServerRegRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.roje.game.message.server_register.ServerRegRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.roje.game.message.server_register.ServerRegRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.roje.game.message.server_register.ServerRegRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.roje.game.message.server_register.ServerRegRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.roje.game.message.server_register.ServerRegRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.roje.game.message.server_register.ServerRegRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.roje.game.message.server_register.ServerRegRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.roje.game.message.server_register.ServerRegRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.roje.game.message.server_register.ServerRegRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.roje.game.message.server_register.ServerRegRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.roje.game.message.server_register.ServerRegRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.roje.game.message.server_register.ServerRegRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code ServerRegRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:ServerRegRequest)
      com.roje.game.message.server_register.ServerRegRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.roje.game.message.server_register.ServerRegister.internal_static_ServerRegRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.roje.game.message.server_register.ServerRegister.internal_static_ServerRegRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.roje.game.message.server_register.ServerRegRequest.class, com.roje.game.message.server_register.ServerRegRequest.Builder.class);
    }

    // Construct using com.roje.game.message.server_register.ServerRegRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (serverInfoBuilder_ == null) {
        serverInfo_ = null;
      } else {
        serverInfo_ = null;
        serverInfoBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.roje.game.message.server_register.ServerRegister.internal_static_ServerRegRequest_descriptor;
    }

    @java.lang.Override
    public com.roje.game.message.server_register.ServerRegRequest getDefaultInstanceForType() {
      return com.roje.game.message.server_register.ServerRegRequest.getDefaultInstance();
    }

    @java.lang.Override
    public com.roje.game.message.server_register.ServerRegRequest build() {
      com.roje.game.message.server_register.ServerRegRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.roje.game.message.server_register.ServerRegRequest buildPartial() {
      com.roje.game.message.server_register.ServerRegRequest result = new com.roje.game.message.server_register.ServerRegRequest(this);
      if (serverInfoBuilder_ == null) {
        result.serverInfo_ = serverInfo_;
      } else {
        result.serverInfo_ = serverInfoBuilder_.build();
      }
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return (Builder) super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.roje.game.message.server_register.ServerRegRequest) {
        return mergeFrom((com.roje.game.message.server_register.ServerRegRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.roje.game.message.server_register.ServerRegRequest other) {
      if (other == com.roje.game.message.server_register.ServerRegRequest.getDefaultInstance()) return this;
      if (other.hasServerInfo()) {
        mergeServerInfo(other.getServerInfo());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.roje.game.message.server_register.ServerRegRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.roje.game.message.server_register.ServerRegRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private com.roje.game.message.server_info.ServerInfo serverInfo_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.roje.game.message.server_info.ServerInfo, com.roje.game.message.server_info.ServerInfo.Builder, com.roje.game.message.server_info.ServerInfoOrBuilder> serverInfoBuilder_;
    /**
     * <code>.ServerInfo serverInfo = 1;</code>
     */
    public boolean hasServerInfo() {
      return serverInfoBuilder_ != null || serverInfo_ != null;
    }
    /**
     * <code>.ServerInfo serverInfo = 1;</code>
     */
    public com.roje.game.message.server_info.ServerInfo getServerInfo() {
      if (serverInfoBuilder_ == null) {
        return serverInfo_ == null ? com.roje.game.message.server_info.ServerInfo.getDefaultInstance() : serverInfo_;
      } else {
        return serverInfoBuilder_.getMessage();
      }
    }
    /**
     * <code>.ServerInfo serverInfo = 1;</code>
     */
    public Builder setServerInfo(com.roje.game.message.server_info.ServerInfo value) {
      if (serverInfoBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        serverInfo_ = value;
        onChanged();
      } else {
        serverInfoBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.ServerInfo serverInfo = 1;</code>
     */
    public Builder setServerInfo(
        com.roje.game.message.server_info.ServerInfo.Builder builderForValue) {
      if (serverInfoBuilder_ == null) {
        serverInfo_ = builderForValue.build();
        onChanged();
      } else {
        serverInfoBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.ServerInfo serverInfo = 1;</code>
     */
    public Builder mergeServerInfo(com.roje.game.message.server_info.ServerInfo value) {
      if (serverInfoBuilder_ == null) {
        if (serverInfo_ != null) {
          serverInfo_ =
            com.roje.game.message.server_info.ServerInfo.newBuilder(serverInfo_).mergeFrom(value).buildPartial();
        } else {
          serverInfo_ = value;
        }
        onChanged();
      } else {
        serverInfoBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.ServerInfo serverInfo = 1;</code>
     */
    public Builder clearServerInfo() {
      if (serverInfoBuilder_ == null) {
        serverInfo_ = null;
        onChanged();
      } else {
        serverInfo_ = null;
        serverInfoBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.ServerInfo serverInfo = 1;</code>
     */
    public com.roje.game.message.server_info.ServerInfo.Builder getServerInfoBuilder() {
      
      onChanged();
      return getServerInfoFieldBuilder().getBuilder();
    }
    /**
     * <code>.ServerInfo serverInfo = 1;</code>
     */
    public com.roje.game.message.server_info.ServerInfoOrBuilder getServerInfoOrBuilder() {
      if (serverInfoBuilder_ != null) {
        return serverInfoBuilder_.getMessageOrBuilder();
      } else {
        return serverInfo_ == null ?
            com.roje.game.message.server_info.ServerInfo.getDefaultInstance() : serverInfo_;
      }
    }
    /**
     * <code>.ServerInfo serverInfo = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.roje.game.message.server_info.ServerInfo, com.roje.game.message.server_info.ServerInfo.Builder, com.roje.game.message.server_info.ServerInfoOrBuilder> 
        getServerInfoFieldBuilder() {
      if (serverInfoBuilder_ == null) {
        serverInfoBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.roje.game.message.server_info.ServerInfo, com.roje.game.message.server_info.ServerInfo.Builder, com.roje.game.message.server_info.ServerInfoOrBuilder>(
                getServerInfo(),
                getParentForChildren(),
                isClean());
        serverInfo_ = null;
      }
      return serverInfoBuilder_;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFieldsProto3(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:ServerRegRequest)
  }

  // @@protoc_insertion_point(class_scope:ServerRegRequest)
  private static final com.roje.game.message.server_register.ServerRegRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.roje.game.message.server_register.ServerRegRequest();
  }

  public static com.roje.game.message.server_register.ServerRegRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ServerRegRequest>
      PARSER = new com.google.protobuf.AbstractParser<ServerRegRequest>() {
    @java.lang.Override
    public ServerRegRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ServerRegRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ServerRegRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ServerRegRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.roje.game.message.server_register.ServerRegRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
