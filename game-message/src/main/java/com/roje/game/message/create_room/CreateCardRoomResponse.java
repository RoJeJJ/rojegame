// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: room.proto

package com.roje.game.message.create_room;

/**
 * Protobuf type {@code CreateCardRoomResponse}
 */
public  final class CreateCardRoomResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:CreateCardRoomResponse)
    CreateCardRoomResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use CreateCardRoomResponse.newBuilder() to construct.
  private CreateCardRoomResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private CreateCardRoomResponse() {
    code_ = 0;
    msg_ = "";
    account_ = "";
    type_ = 0;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private CreateCardRoomResponse(
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
          case 8: {

            code_ = input.readInt32();
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            msg_ = s;
            break;
          }
          case 26: {
            com.google.protobuf.Any.Builder subBuilder = null;
            if (responseData_ != null) {
              subBuilder = responseData_.toBuilder();
            }
            responseData_ = input.readMessage(com.google.protobuf.Any.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(responseData_);
              responseData_ = subBuilder.buildPartial();
            }

            break;
          }
          case 34: {
            java.lang.String s = input.readStringRequireUtf8();

            account_ = s;
            break;
          }
          case 40: {

            type_ = input.readInt32();
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
    return com.roje.game.message.create_room.Room.internal_static_CreateCardRoomResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.roje.game.message.create_room.Room.internal_static_CreateCardRoomResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.roje.game.message.create_room.CreateCardRoomResponse.class, com.roje.game.message.create_room.CreateCardRoomResponse.Builder.class);
  }

  public static final int CODE_FIELD_NUMBER = 1;
  private int code_;
  /**
   * <code>int32 code = 1;</code>
   */
  public int getCode() {
    return code_;
  }

  public static final int MSG_FIELD_NUMBER = 2;
  private volatile java.lang.Object msg_;
  /**
   * <code>string msg = 2;</code>
   */
  public java.lang.String getMsg() {
    java.lang.Object ref = msg_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      msg_ = s;
      return s;
    }
  }
  /**
   * <code>string msg = 2;</code>
   */
  public com.google.protobuf.ByteString
      getMsgBytes() {
    java.lang.Object ref = msg_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      msg_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int RESPONSEDATA_FIELD_NUMBER = 3;
  private com.google.protobuf.Any responseData_;
  /**
   * <code>.google.protobuf.Any responseData = 3;</code>
   */
  public boolean hasResponseData() {
    return responseData_ != null;
  }
  /**
   * <code>.google.protobuf.Any responseData = 3;</code>
   */
  public com.google.protobuf.Any getResponseData() {
    return responseData_ == null ? com.google.protobuf.Any.getDefaultInstance() : responseData_;
  }
  /**
   * <code>.google.protobuf.Any responseData = 3;</code>
   */
  public com.google.protobuf.AnyOrBuilder getResponseDataOrBuilder() {
    return getResponseData();
  }

  public static final int ACCOUNT_FIELD_NUMBER = 4;
  private volatile java.lang.Object account_;
  /**
   * <code>string account = 4;</code>
   */
  public java.lang.String getAccount() {
    java.lang.Object ref = account_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      account_ = s;
      return s;
    }
  }
  /**
   * <code>string account = 4;</code>
   */
  public com.google.protobuf.ByteString
      getAccountBytes() {
    java.lang.Object ref = account_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      account_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TYPE_FIELD_NUMBER = 5;
  private int type_;
  /**
   * <code>int32 type = 5;</code>
   */
  public int getType() {
    return type_;
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
    if (code_ != 0) {
      output.writeInt32(1, code_);
    }
    if (!getMsgBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, msg_);
    }
    if (responseData_ != null) {
      output.writeMessage(3, getResponseData());
    }
    if (!getAccountBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, account_);
    }
    if (type_ != 0) {
      output.writeInt32(5, type_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (code_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, code_);
    }
    if (!getMsgBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, msg_);
    }
    if (responseData_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(3, getResponseData());
    }
    if (!getAccountBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, account_);
    }
    if (type_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(5, type_);
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
    if (!(obj instanceof com.roje.game.message.create_room.CreateCardRoomResponse)) {
      return super.equals(obj);
    }
    com.roje.game.message.create_room.CreateCardRoomResponse other = (com.roje.game.message.create_room.CreateCardRoomResponse) obj;

    boolean result = true;
    result = result && (getCode()
        == other.getCode());
    result = result && getMsg()
        .equals(other.getMsg());
    result = result && (hasResponseData() == other.hasResponseData());
    if (hasResponseData()) {
      result = result && getResponseData()
          .equals(other.getResponseData());
    }
    result = result && getAccount()
        .equals(other.getAccount());
    result = result && (getType()
        == other.getType());
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
    hash = (37 * hash) + CODE_FIELD_NUMBER;
    hash = (53 * hash) + getCode();
    hash = (37 * hash) + MSG_FIELD_NUMBER;
    hash = (53 * hash) + getMsg().hashCode();
    if (hasResponseData()) {
      hash = (37 * hash) + RESPONSEDATA_FIELD_NUMBER;
      hash = (53 * hash) + getResponseData().hashCode();
    }
    hash = (37 * hash) + ACCOUNT_FIELD_NUMBER;
    hash = (53 * hash) + getAccount().hashCode();
    hash = (37 * hash) + TYPE_FIELD_NUMBER;
    hash = (53 * hash) + getType();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.roje.game.message.create_room.CreateCardRoomResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.roje.game.message.create_room.CreateCardRoomResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.roje.game.message.create_room.CreateCardRoomResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.roje.game.message.create_room.CreateCardRoomResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.roje.game.message.create_room.CreateCardRoomResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.roje.game.message.create_room.CreateCardRoomResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.roje.game.message.create_room.CreateCardRoomResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.roje.game.message.create_room.CreateCardRoomResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.roje.game.message.create_room.CreateCardRoomResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.roje.game.message.create_room.CreateCardRoomResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.roje.game.message.create_room.CreateCardRoomResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.roje.game.message.create_room.CreateCardRoomResponse parseFrom(
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
  public static Builder newBuilder(com.roje.game.message.create_room.CreateCardRoomResponse prototype) {
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
   * Protobuf type {@code CreateCardRoomResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:CreateCardRoomResponse)
      com.roje.game.message.create_room.CreateCardRoomResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.roje.game.message.create_room.Room.internal_static_CreateCardRoomResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.roje.game.message.create_room.Room.internal_static_CreateCardRoomResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.roje.game.message.create_room.CreateCardRoomResponse.class, com.roje.game.message.create_room.CreateCardRoomResponse.Builder.class);
    }

    // Construct using com.roje.game.message.create_room.CreateCardRoomResponse.newBuilder()
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
      code_ = 0;

      msg_ = "";

      if (responseDataBuilder_ == null) {
        responseData_ = null;
      } else {
        responseData_ = null;
        responseDataBuilder_ = null;
      }
      account_ = "";

      type_ = 0;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.roje.game.message.create_room.Room.internal_static_CreateCardRoomResponse_descriptor;
    }

    @java.lang.Override
    public com.roje.game.message.create_room.CreateCardRoomResponse getDefaultInstanceForType() {
      return com.roje.game.message.create_room.CreateCardRoomResponse.getDefaultInstance();
    }

    @java.lang.Override
    public com.roje.game.message.create_room.CreateCardRoomResponse build() {
      com.roje.game.message.create_room.CreateCardRoomResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.roje.game.message.create_room.CreateCardRoomResponse buildPartial() {
      com.roje.game.message.create_room.CreateCardRoomResponse result = new com.roje.game.message.create_room.CreateCardRoomResponse(this);
      result.code_ = code_;
      result.msg_ = msg_;
      if (responseDataBuilder_ == null) {
        result.responseData_ = responseData_;
      } else {
        result.responseData_ = responseDataBuilder_.build();
      }
      result.account_ = account_;
      result.type_ = type_;
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
      if (other instanceof com.roje.game.message.create_room.CreateCardRoomResponse) {
        return mergeFrom((com.roje.game.message.create_room.CreateCardRoomResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.roje.game.message.create_room.CreateCardRoomResponse other) {
      if (other == com.roje.game.message.create_room.CreateCardRoomResponse.getDefaultInstance()) return this;
      if (other.getCode() != 0) {
        setCode(other.getCode());
      }
      if (!other.getMsg().isEmpty()) {
        msg_ = other.msg_;
        onChanged();
      }
      if (other.hasResponseData()) {
        mergeResponseData(other.getResponseData());
      }
      if (!other.getAccount().isEmpty()) {
        account_ = other.account_;
        onChanged();
      }
      if (other.getType() != 0) {
        setType(other.getType());
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
      com.roje.game.message.create_room.CreateCardRoomResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.roje.game.message.create_room.CreateCardRoomResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int code_ ;
    /**
     * <code>int32 code = 1;</code>
     */
    public int getCode() {
      return code_;
    }
    /**
     * <code>int32 code = 1;</code>
     */
    public Builder setCode(int value) {
      
      code_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 code = 1;</code>
     */
    public Builder clearCode() {
      
      code_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object msg_ = "";
    /**
     * <code>string msg = 2;</code>
     */
    public java.lang.String getMsg() {
      java.lang.Object ref = msg_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        msg_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string msg = 2;</code>
     */
    public com.google.protobuf.ByteString
        getMsgBytes() {
      java.lang.Object ref = msg_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        msg_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string msg = 2;</code>
     */
    public Builder setMsg(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      msg_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string msg = 2;</code>
     */
    public Builder clearMsg() {
      
      msg_ = getDefaultInstance().getMsg();
      onChanged();
      return this;
    }
    /**
     * <code>string msg = 2;</code>
     */
    public Builder setMsgBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      msg_ = value;
      onChanged();
      return this;
    }

    private com.google.protobuf.Any responseData_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder> responseDataBuilder_;
    /**
     * <code>.google.protobuf.Any responseData = 3;</code>
     */
    public boolean hasResponseData() {
      return responseDataBuilder_ != null || responseData_ != null;
    }
    /**
     * <code>.google.protobuf.Any responseData = 3;</code>
     */
    public com.google.protobuf.Any getResponseData() {
      if (responseDataBuilder_ == null) {
        return responseData_ == null ? com.google.protobuf.Any.getDefaultInstance() : responseData_;
      } else {
        return responseDataBuilder_.getMessage();
      }
    }
    /**
     * <code>.google.protobuf.Any responseData = 3;</code>
     */
    public Builder setResponseData(com.google.protobuf.Any value) {
      if (responseDataBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        responseData_ = value;
        onChanged();
      } else {
        responseDataBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.google.protobuf.Any responseData = 3;</code>
     */
    public Builder setResponseData(
        com.google.protobuf.Any.Builder builderForValue) {
      if (responseDataBuilder_ == null) {
        responseData_ = builderForValue.build();
        onChanged();
      } else {
        responseDataBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.google.protobuf.Any responseData = 3;</code>
     */
    public Builder mergeResponseData(com.google.protobuf.Any value) {
      if (responseDataBuilder_ == null) {
        if (responseData_ != null) {
          responseData_ =
            com.google.protobuf.Any.newBuilder(responseData_).mergeFrom(value).buildPartial();
        } else {
          responseData_ = value;
        }
        onChanged();
      } else {
        responseDataBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.google.protobuf.Any responseData = 3;</code>
     */
    public Builder clearResponseData() {
      if (responseDataBuilder_ == null) {
        responseData_ = null;
        onChanged();
      } else {
        responseData_ = null;
        responseDataBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.google.protobuf.Any responseData = 3;</code>
     */
    public com.google.protobuf.Any.Builder getResponseDataBuilder() {
      
      onChanged();
      return getResponseDataFieldBuilder().getBuilder();
    }
    /**
     * <code>.google.protobuf.Any responseData = 3;</code>
     */
    public com.google.protobuf.AnyOrBuilder getResponseDataOrBuilder() {
      if (responseDataBuilder_ != null) {
        return responseDataBuilder_.getMessageOrBuilder();
      } else {
        return responseData_ == null ?
            com.google.protobuf.Any.getDefaultInstance() : responseData_;
      }
    }
    /**
     * <code>.google.protobuf.Any responseData = 3;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder> 
        getResponseDataFieldBuilder() {
      if (responseDataBuilder_ == null) {
        responseDataBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder>(
                getResponseData(),
                getParentForChildren(),
                isClean());
        responseData_ = null;
      }
      return responseDataBuilder_;
    }

    private java.lang.Object account_ = "";
    /**
     * <code>string account = 4;</code>
     */
    public java.lang.String getAccount() {
      java.lang.Object ref = account_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        account_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string account = 4;</code>
     */
    public com.google.protobuf.ByteString
        getAccountBytes() {
      java.lang.Object ref = account_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        account_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string account = 4;</code>
     */
    public Builder setAccount(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      account_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string account = 4;</code>
     */
    public Builder clearAccount() {
      
      account_ = getDefaultInstance().getAccount();
      onChanged();
      return this;
    }
    /**
     * <code>string account = 4;</code>
     */
    public Builder setAccountBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      account_ = value;
      onChanged();
      return this;
    }

    private int type_ ;
    /**
     * <code>int32 type = 5;</code>
     */
    public int getType() {
      return type_;
    }
    /**
     * <code>int32 type = 5;</code>
     */
    public Builder setType(int value) {
      
      type_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 type = 5;</code>
     */
    public Builder clearType() {
      
      type_ = 0;
      onChanged();
      return this;
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


    // @@protoc_insertion_point(builder_scope:CreateCardRoomResponse)
  }

  // @@protoc_insertion_point(class_scope:CreateCardRoomResponse)
  private static final com.roje.game.message.create_room.CreateCardRoomResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.roje.game.message.create_room.CreateCardRoomResponse();
  }

  public static com.roje.game.message.create_room.CreateCardRoomResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<CreateCardRoomResponse>
      PARSER = new com.google.protobuf.AbstractParser<CreateCardRoomResponse>() {
    @java.lang.Override
    public CreateCardRoomResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new CreateCardRoomResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<CreateCardRoomResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<CreateCardRoomResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.roje.game.message.create_room.CreateCardRoomResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

