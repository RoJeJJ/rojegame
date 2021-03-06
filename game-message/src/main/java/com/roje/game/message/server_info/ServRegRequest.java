// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: server.proto

package com.roje.game.message.server_info;

/**
 * Protobuf type {@code ServRegRequest}
 */
public  final class ServRegRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:ServRegRequest)
    ServRegRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ServRegRequest.newBuilder() to construct.
  private ServRegRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ServRegRequest() {
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ServRegRequest(
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
            com.roje.game.message.server_info.ServInfo.Builder subBuilder = null;
            if (servInfo_ != null) {
              subBuilder = servInfo_.toBuilder();
            }
            servInfo_ = input.readMessage(com.roje.game.message.server_info.ServInfo.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(servInfo_);
              servInfo_ = subBuilder.buildPartial();
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
    return com.roje.game.message.server_info.Server.internal_static_ServRegRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.roje.game.message.server_info.Server.internal_static_ServRegRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.roje.game.message.server_info.ServRegRequest.class, com.roje.game.message.server_info.ServRegRequest.Builder.class);
  }

  public static final int SERVINFO_FIELD_NUMBER = 1;
  private com.roje.game.message.server_info.ServInfo servInfo_;
  /**
   * <code>.ServInfo servInfo = 1;</code>
   */
  public boolean hasServInfo() {
    return servInfo_ != null;
  }
  /**
   * <code>.ServInfo servInfo = 1;</code>
   */
  public com.roje.game.message.server_info.ServInfo getServInfo() {
    return servInfo_ == null ? com.roje.game.message.server_info.ServInfo.getDefaultInstance() : servInfo_;
  }
  /**
   * <code>.ServInfo servInfo = 1;</code>
   */
  public com.roje.game.message.server_info.ServInfoOrBuilder getServInfoOrBuilder() {
    return getServInfo();
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
    if (servInfo_ != null) {
      output.writeMessage(1, getServInfo());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (servInfo_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getServInfo());
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
    if (!(obj instanceof com.roje.game.message.server_info.ServRegRequest)) {
      return super.equals(obj);
    }
    com.roje.game.message.server_info.ServRegRequest other = (com.roje.game.message.server_info.ServRegRequest) obj;

    boolean result = true;
    result = result && (hasServInfo() == other.hasServInfo());
    if (hasServInfo()) {
      result = result && getServInfo()
          .equals(other.getServInfo());
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
    if (hasServInfo()) {
      hash = (37 * hash) + SERVINFO_FIELD_NUMBER;
      hash = (53 * hash) + getServInfo().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.roje.game.message.server_info.ServRegRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.roje.game.message.server_info.ServRegRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.roje.game.message.server_info.ServRegRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.roje.game.message.server_info.ServRegRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.roje.game.message.server_info.ServRegRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.roje.game.message.server_info.ServRegRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.roje.game.message.server_info.ServRegRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.roje.game.message.server_info.ServRegRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.roje.game.message.server_info.ServRegRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.roje.game.message.server_info.ServRegRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.roje.game.message.server_info.ServRegRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.roje.game.message.server_info.ServRegRequest parseFrom(
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
  public static Builder newBuilder(com.roje.game.message.server_info.ServRegRequest prototype) {
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
   * Protobuf type {@code ServRegRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:ServRegRequest)
      com.roje.game.message.server_info.ServRegRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.roje.game.message.server_info.Server.internal_static_ServRegRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.roje.game.message.server_info.Server.internal_static_ServRegRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.roje.game.message.server_info.ServRegRequest.class, com.roje.game.message.server_info.ServRegRequest.Builder.class);
    }

    // Construct using com.roje.game.message.server_info.ServRegRequest.newBuilder()
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
      if (servInfoBuilder_ == null) {
        servInfo_ = null;
      } else {
        servInfo_ = null;
        servInfoBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.roje.game.message.server_info.Server.internal_static_ServRegRequest_descriptor;
    }

    @java.lang.Override
    public com.roje.game.message.server_info.ServRegRequest getDefaultInstanceForType() {
      return com.roje.game.message.server_info.ServRegRequest.getDefaultInstance();
    }

    @java.lang.Override
    public com.roje.game.message.server_info.ServRegRequest build() {
      com.roje.game.message.server_info.ServRegRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.roje.game.message.server_info.ServRegRequest buildPartial() {
      com.roje.game.message.server_info.ServRegRequest result = new com.roje.game.message.server_info.ServRegRequest(this);
      if (servInfoBuilder_ == null) {
        result.servInfo_ = servInfo_;
      } else {
        result.servInfo_ = servInfoBuilder_.build();
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
      if (other instanceof com.roje.game.message.server_info.ServRegRequest) {
        return mergeFrom((com.roje.game.message.server_info.ServRegRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.roje.game.message.server_info.ServRegRequest other) {
      if (other == com.roje.game.message.server_info.ServRegRequest.getDefaultInstance()) return this;
      if (other.hasServInfo()) {
        mergeServInfo(other.getServInfo());
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
      com.roje.game.message.server_info.ServRegRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.roje.game.message.server_info.ServRegRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private com.roje.game.message.server_info.ServInfo servInfo_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.roje.game.message.server_info.ServInfo, com.roje.game.message.server_info.ServInfo.Builder, com.roje.game.message.server_info.ServInfoOrBuilder> servInfoBuilder_;
    /**
     * <code>.ServInfo servInfo = 1;</code>
     */
    public boolean hasServInfo() {
      return servInfoBuilder_ != null || servInfo_ != null;
    }
    /**
     * <code>.ServInfo servInfo = 1;</code>
     */
    public com.roje.game.message.server_info.ServInfo getServInfo() {
      if (servInfoBuilder_ == null) {
        return servInfo_ == null ? com.roje.game.message.server_info.ServInfo.getDefaultInstance() : servInfo_;
      } else {
        return servInfoBuilder_.getMessage();
      }
    }
    /**
     * <code>.ServInfo servInfo = 1;</code>
     */
    public Builder setServInfo(com.roje.game.message.server_info.ServInfo value) {
      if (servInfoBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        servInfo_ = value;
        onChanged();
      } else {
        servInfoBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.ServInfo servInfo = 1;</code>
     */
    public Builder setServInfo(
        com.roje.game.message.server_info.ServInfo.Builder builderForValue) {
      if (servInfoBuilder_ == null) {
        servInfo_ = builderForValue.build();
        onChanged();
      } else {
        servInfoBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.ServInfo servInfo = 1;</code>
     */
    public Builder mergeServInfo(com.roje.game.message.server_info.ServInfo value) {
      if (servInfoBuilder_ == null) {
        if (servInfo_ != null) {
          servInfo_ =
            com.roje.game.message.server_info.ServInfo.newBuilder(servInfo_).mergeFrom(value).buildPartial();
        } else {
          servInfo_ = value;
        }
        onChanged();
      } else {
        servInfoBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.ServInfo servInfo = 1;</code>
     */
    public Builder clearServInfo() {
      if (servInfoBuilder_ == null) {
        servInfo_ = null;
        onChanged();
      } else {
        servInfo_ = null;
        servInfoBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.ServInfo servInfo = 1;</code>
     */
    public com.roje.game.message.server_info.ServInfo.Builder getServInfoBuilder() {
      
      onChanged();
      return getServInfoFieldBuilder().getBuilder();
    }
    /**
     * <code>.ServInfo servInfo = 1;</code>
     */
    public com.roje.game.message.server_info.ServInfoOrBuilder getServInfoOrBuilder() {
      if (servInfoBuilder_ != null) {
        return servInfoBuilder_.getMessageOrBuilder();
      } else {
        return servInfo_ == null ?
            com.roje.game.message.server_info.ServInfo.getDefaultInstance() : servInfo_;
      }
    }
    /**
     * <code>.ServInfo servInfo = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.roje.game.message.server_info.ServInfo, com.roje.game.message.server_info.ServInfo.Builder, com.roje.game.message.server_info.ServInfoOrBuilder> 
        getServInfoFieldBuilder() {
      if (servInfoBuilder_ == null) {
        servInfoBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.roje.game.message.server_info.ServInfo, com.roje.game.message.server_info.ServInfo.Builder, com.roje.game.message.server_info.ServInfoOrBuilder>(
                getServInfo(),
                getParentForChildren(),
                isClean());
        servInfo_ = null;
      }
      return servInfoBuilder_;
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


    // @@protoc_insertion_point(builder_scope:ServRegRequest)
  }

  // @@protoc_insertion_point(class_scope:ServRegRequest)
  private static final com.roje.game.message.server_info.ServRegRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.roje.game.message.server_info.ServRegRequest();
  }

  public static com.roje.game.message.server_info.ServRegRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ServRegRequest>
      PARSER = new com.google.protobuf.AbstractParser<ServRegRequest>() {
    @java.lang.Override
    public ServRegRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ServRegRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ServRegRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ServRegRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.roje.game.message.server_info.ServRegRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

