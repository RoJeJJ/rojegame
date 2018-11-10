// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: golden-flaud.proto

package com.roje.game.message.gf;

/**
 * Protobuf type {@code ActionRequest}
 */
public  final class ActionRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:ActionRequest)
    ActionRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ActionRequest.newBuilder() to construct.
  private ActionRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ActionRequest() {
    action_ = 0;
    add_ = 0;
    id_ = 0L;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ActionRequest(
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

            action_ = input.readInt32();
            break;
          }
          case 16: {

            add_ = input.readInt32();
            break;
          }
          case 24: {

            id_ = input.readInt64();
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
    return com.roje.game.message.gf.GoldenFlaud.internal_static_ActionRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.roje.game.message.gf.GoldenFlaud.internal_static_ActionRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.roje.game.message.gf.ActionRequest.class, com.roje.game.message.gf.ActionRequest.Builder.class);
  }

  public static final int ACTION_FIELD_NUMBER = 1;
  private int action_;
  /**
   * <pre>
   *1.看牌,2.弃牌,3.跟注,4.加注,5.比牌
   * </pre>
   *
   * <code>int32 action = 1;</code>
   */
  public int getAction() {
    return action_;
  }

  public static final int ADD_FIELD_NUMBER = 2;
  private int add_;
  /**
   * <code>int32 add = 2;</code>
   */
  public int getAdd() {
    return add_;
  }

  public static final int ID_FIELD_NUMBER = 3;
  private long id_;
  /**
   * <code>int64 id = 3;</code>
   */
  public long getId() {
    return id_;
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
    if (action_ != 0) {
      output.writeInt32(1, action_);
    }
    if (add_ != 0) {
      output.writeInt32(2, add_);
    }
    if (id_ != 0L) {
      output.writeInt64(3, id_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (action_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, action_);
    }
    if (add_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, add_);
    }
    if (id_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(3, id_);
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
    if (!(obj instanceof com.roje.game.message.gf.ActionRequest)) {
      return super.equals(obj);
    }
    com.roje.game.message.gf.ActionRequest other = (com.roje.game.message.gf.ActionRequest) obj;

    boolean result = true;
    result = result && (getAction()
        == other.getAction());
    result = result && (getAdd()
        == other.getAdd());
    result = result && (getId()
        == other.getId());
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
    hash = (37 * hash) + ACTION_FIELD_NUMBER;
    hash = (53 * hash) + getAction();
    hash = (37 * hash) + ADD_FIELD_NUMBER;
    hash = (53 * hash) + getAdd();
    hash = (37 * hash) + ID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getId());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.roje.game.message.gf.ActionRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.roje.game.message.gf.ActionRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.roje.game.message.gf.ActionRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.roje.game.message.gf.ActionRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.roje.game.message.gf.ActionRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.roje.game.message.gf.ActionRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.roje.game.message.gf.ActionRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.roje.game.message.gf.ActionRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.roje.game.message.gf.ActionRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.roje.game.message.gf.ActionRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.roje.game.message.gf.ActionRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.roje.game.message.gf.ActionRequest parseFrom(
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
  public static Builder newBuilder(com.roje.game.message.gf.ActionRequest prototype) {
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
   * Protobuf type {@code ActionRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:ActionRequest)
      com.roje.game.message.gf.ActionRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.roje.game.message.gf.GoldenFlaud.internal_static_ActionRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.roje.game.message.gf.GoldenFlaud.internal_static_ActionRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.roje.game.message.gf.ActionRequest.class, com.roje.game.message.gf.ActionRequest.Builder.class);
    }

    // Construct using com.roje.game.message.gf.ActionRequest.newBuilder()
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
      action_ = 0;

      add_ = 0;

      id_ = 0L;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.roje.game.message.gf.GoldenFlaud.internal_static_ActionRequest_descriptor;
    }

    @java.lang.Override
    public com.roje.game.message.gf.ActionRequest getDefaultInstanceForType() {
      return com.roje.game.message.gf.ActionRequest.getDefaultInstance();
    }

    @java.lang.Override
    public com.roje.game.message.gf.ActionRequest build() {
      com.roje.game.message.gf.ActionRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.roje.game.message.gf.ActionRequest buildPartial() {
      com.roje.game.message.gf.ActionRequest result = new com.roje.game.message.gf.ActionRequest(this);
      result.action_ = action_;
      result.add_ = add_;
      result.id_ = id_;
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
      if (other instanceof com.roje.game.message.gf.ActionRequest) {
        return mergeFrom((com.roje.game.message.gf.ActionRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.roje.game.message.gf.ActionRequest other) {
      if (other == com.roje.game.message.gf.ActionRequest.getDefaultInstance()) return this;
      if (other.getAction() != 0) {
        setAction(other.getAction());
      }
      if (other.getAdd() != 0) {
        setAdd(other.getAdd());
      }
      if (other.getId() != 0L) {
        setId(other.getId());
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
      com.roje.game.message.gf.ActionRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.roje.game.message.gf.ActionRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int action_ ;
    /**
     * <pre>
     *1.看牌,2.弃牌,3.跟注,4.加注,5.比牌
     * </pre>
     *
     * <code>int32 action = 1;</code>
     */
    public int getAction() {
      return action_;
    }
    /**
     * <pre>
     *1.看牌,2.弃牌,3.跟注,4.加注,5.比牌
     * </pre>
     *
     * <code>int32 action = 1;</code>
     */
    public Builder setAction(int value) {
      
      action_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *1.看牌,2.弃牌,3.跟注,4.加注,5.比牌
     * </pre>
     *
     * <code>int32 action = 1;</code>
     */
    public Builder clearAction() {
      
      action_ = 0;
      onChanged();
      return this;
    }

    private int add_ ;
    /**
     * <code>int32 add = 2;</code>
     */
    public int getAdd() {
      return add_;
    }
    /**
     * <code>int32 add = 2;</code>
     */
    public Builder setAdd(int value) {
      
      add_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 add = 2;</code>
     */
    public Builder clearAdd() {
      
      add_ = 0;
      onChanged();
      return this;
    }

    private long id_ ;
    /**
     * <code>int64 id = 3;</code>
     */
    public long getId() {
      return id_;
    }
    /**
     * <code>int64 id = 3;</code>
     */
    public Builder setId(long value) {
      
      id_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 id = 3;</code>
     */
    public Builder clearId() {
      
      id_ = 0L;
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


    // @@protoc_insertion_point(builder_scope:ActionRequest)
  }

  // @@protoc_insertion_point(class_scope:ActionRequest)
  private static final com.roje.game.message.gf.ActionRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.roje.game.message.gf.ActionRequest();
  }

  public static com.roje.game.message.gf.ActionRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ActionRequest>
      PARSER = new com.google.protobuf.AbstractParser<ActionRequest>() {
    @java.lang.Override
    public ActionRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ActionRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ActionRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ActionRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.roje.game.message.gf.ActionRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
