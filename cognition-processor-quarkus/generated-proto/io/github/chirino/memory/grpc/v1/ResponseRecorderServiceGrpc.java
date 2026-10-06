package io.github.chirino.memory.grpc.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: memory/v1/memory_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ResponseRecorderServiceGrpc {

  private ResponseRecorderServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "memory.v1.ResponseRecorderService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.RecordRequest,
      io.github.chirino.memory.grpc.v1.RecordResponse> getRecordMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Record",
      requestType = io.github.chirino.memory.grpc.v1.RecordRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.RecordResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.RecordRequest,
      io.github.chirino.memory.grpc.v1.RecordResponse> getRecordMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.RecordRequest, io.github.chirino.memory.grpc.v1.RecordResponse> getRecordMethod;
    if ((getRecordMethod = ResponseRecorderServiceGrpc.getRecordMethod) == null) {
      synchronized (ResponseRecorderServiceGrpc.class) {
        if ((getRecordMethod = ResponseRecorderServiceGrpc.getRecordMethod) == null) {
          ResponseRecorderServiceGrpc.getRecordMethod = getRecordMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.RecordRequest, io.github.chirino.memory.grpc.v1.RecordResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Record"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.RecordRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.RecordResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ResponseRecorderServiceMethodDescriptorSupplier("Record"))
              .build();
        }
      }
    }
    return getRecordMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ReplayRequest,
      io.github.chirino.memory.grpc.v1.ReplayResponse> getReplayMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Replay",
      requestType = io.github.chirino.memory.grpc.v1.ReplayRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.ReplayResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ReplayRequest,
      io.github.chirino.memory.grpc.v1.ReplayResponse> getReplayMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ReplayRequest, io.github.chirino.memory.grpc.v1.ReplayResponse> getReplayMethod;
    if ((getReplayMethod = ResponseRecorderServiceGrpc.getReplayMethod) == null) {
      synchronized (ResponseRecorderServiceGrpc.class) {
        if ((getReplayMethod = ResponseRecorderServiceGrpc.getReplayMethod) == null) {
          ResponseRecorderServiceGrpc.getReplayMethod = getReplayMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.ReplayRequest, io.github.chirino.memory.grpc.v1.ReplayResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Replay"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ReplayRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ReplayResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ResponseRecorderServiceMethodDescriptorSupplier("Replay"))
              .build();
        }
      }
    }
    return getReplayMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.CancelRecordRequest,
      io.github.chirino.memory.grpc.v1.CancelRecordResponse> getCancelMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Cancel",
      requestType = io.github.chirino.memory.grpc.v1.CancelRecordRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.CancelRecordResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.CancelRecordRequest,
      io.github.chirino.memory.grpc.v1.CancelRecordResponse> getCancelMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.CancelRecordRequest, io.github.chirino.memory.grpc.v1.CancelRecordResponse> getCancelMethod;
    if ((getCancelMethod = ResponseRecorderServiceGrpc.getCancelMethod) == null) {
      synchronized (ResponseRecorderServiceGrpc.class) {
        if ((getCancelMethod = ResponseRecorderServiceGrpc.getCancelMethod) == null) {
          ResponseRecorderServiceGrpc.getCancelMethod = getCancelMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.CancelRecordRequest, io.github.chirino.memory.grpc.v1.CancelRecordResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Cancel"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.CancelRecordRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.CancelRecordResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ResponseRecorderServiceMethodDescriptorSupplier("Cancel"))
              .build();
        }
      }
    }
    return getCancelMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      io.github.chirino.memory.grpc.v1.IsEnabledResponse> getIsEnabledMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "IsEnabled",
      requestType = com.google.protobuf.Empty.class,
      responseType = io.github.chirino.memory.grpc.v1.IsEnabledResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      io.github.chirino.memory.grpc.v1.IsEnabledResponse> getIsEnabledMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, io.github.chirino.memory.grpc.v1.IsEnabledResponse> getIsEnabledMethod;
    if ((getIsEnabledMethod = ResponseRecorderServiceGrpc.getIsEnabledMethod) == null) {
      synchronized (ResponseRecorderServiceGrpc.class) {
        if ((getIsEnabledMethod = ResponseRecorderServiceGrpc.getIsEnabledMethod) == null) {
          ResponseRecorderServiceGrpc.getIsEnabledMethod = getIsEnabledMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, io.github.chirino.memory.grpc.v1.IsEnabledResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "IsEnabled"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.IsEnabledResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ResponseRecorderServiceMethodDescriptorSupplier("IsEnabled"))
              .build();
        }
      }
    }
    return getIsEnabledMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.CheckRecordingsRequest,
      io.github.chirino.memory.grpc.v1.CheckRecordingsResponse> getCheckRecordingsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CheckRecordings",
      requestType = io.github.chirino.memory.grpc.v1.CheckRecordingsRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.CheckRecordingsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.CheckRecordingsRequest,
      io.github.chirino.memory.grpc.v1.CheckRecordingsResponse> getCheckRecordingsMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.CheckRecordingsRequest, io.github.chirino.memory.grpc.v1.CheckRecordingsResponse> getCheckRecordingsMethod;
    if ((getCheckRecordingsMethod = ResponseRecorderServiceGrpc.getCheckRecordingsMethod) == null) {
      synchronized (ResponseRecorderServiceGrpc.class) {
        if ((getCheckRecordingsMethod = ResponseRecorderServiceGrpc.getCheckRecordingsMethod) == null) {
          ResponseRecorderServiceGrpc.getCheckRecordingsMethod = getCheckRecordingsMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.CheckRecordingsRequest, io.github.chirino.memory.grpc.v1.CheckRecordingsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CheckRecordings"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.CheckRecordingsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.CheckRecordingsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ResponseRecorderServiceMethodDescriptorSupplier("CheckRecordings"))
              .build();
        }
      }
    }
    return getCheckRecordingsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ResponseRecorderServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ResponseRecorderServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ResponseRecorderServiceStub>() {
        @java.lang.Override
        public ResponseRecorderServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ResponseRecorderServiceStub(channel, callOptions);
        }
      };
    return ResponseRecorderServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ResponseRecorderServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ResponseRecorderServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ResponseRecorderServiceBlockingStub>() {
        @java.lang.Override
        public ResponseRecorderServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ResponseRecorderServiceBlockingStub(channel, callOptions);
        }
      };
    return ResponseRecorderServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ResponseRecorderServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ResponseRecorderServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ResponseRecorderServiceFutureStub>() {
        @java.lang.Override
        public ResponseRecorderServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ResponseRecorderServiceFutureStub(channel, callOptions);
        }
      };
    return ResponseRecorderServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * Record response content for a conversation (client streaming, unary response)
     * </pre>
     */
    default io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.RecordRequest> record(
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.RecordResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getRecordMethod(), responseObserver);
    }

    /**
     * <pre>
     * Replay a recording of a conversation
     * </pre>
     */
    default void replay(io.github.chirino.memory.grpc.v1.ReplayRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ReplayResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReplayMethod(), responseObserver);
    }

    /**
     * <pre>
     * Cancel an in-progress recording
     * </pre>
     */
    default void cancel(io.github.chirino.memory.grpc.v1.CancelRecordRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.CancelRecordResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCancelMethod(), responseObserver);
    }

    /**
     * <pre>
     * Check if response recorder is enabled
     * </pre>
     */
    default void isEnabled(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.IsEnabledResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getIsEnabledMethod(), responseObserver);
    }

    /**
     * <pre>
     * Check which conversations have active recordings
     * </pre>
     */
    default void checkRecordings(io.github.chirino.memory.grpc.v1.CheckRecordingsRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.CheckRecordingsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCheckRecordingsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ResponseRecorderService.
   */
  public static abstract class ResponseRecorderServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ResponseRecorderServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ResponseRecorderService.
   */
  public static final class ResponseRecorderServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ResponseRecorderServiceStub> {
    private ResponseRecorderServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ResponseRecorderServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ResponseRecorderServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Record response content for a conversation (client streaming, unary response)
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.RecordRequest> record(
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.RecordResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getRecordMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * Replay a recording of a conversation
     * </pre>
     */
    public void replay(io.github.chirino.memory.grpc.v1.ReplayRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ReplayResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getReplayMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Cancel an in-progress recording
     * </pre>
     */
    public void cancel(io.github.chirino.memory.grpc.v1.CancelRecordRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.CancelRecordResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCancelMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Check if response recorder is enabled
     * </pre>
     */
    public void isEnabled(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.IsEnabledResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getIsEnabledMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Check which conversations have active recordings
     * </pre>
     */
    public void checkRecordings(io.github.chirino.memory.grpc.v1.CheckRecordingsRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.CheckRecordingsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCheckRecordingsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ResponseRecorderService.
   */
  public static final class ResponseRecorderServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ResponseRecorderServiceBlockingStub> {
    private ResponseRecorderServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ResponseRecorderServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ResponseRecorderServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Replay a recording of a conversation
     * </pre>
     */
    public java.util.Iterator<io.github.chirino.memory.grpc.v1.ReplayResponse> replay(
        io.github.chirino.memory.grpc.v1.ReplayRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getReplayMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Cancel an in-progress recording
     * </pre>
     */
    public io.github.chirino.memory.grpc.v1.CancelRecordResponse cancel(io.github.chirino.memory.grpc.v1.CancelRecordRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCancelMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Check if response recorder is enabled
     * </pre>
     */
    public io.github.chirino.memory.grpc.v1.IsEnabledResponse isEnabled(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getIsEnabledMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Check which conversations have active recordings
     * </pre>
     */
    public io.github.chirino.memory.grpc.v1.CheckRecordingsResponse checkRecordings(io.github.chirino.memory.grpc.v1.CheckRecordingsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCheckRecordingsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ResponseRecorderService.
   */
  public static final class ResponseRecorderServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ResponseRecorderServiceFutureStub> {
    private ResponseRecorderServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ResponseRecorderServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ResponseRecorderServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Cancel an in-progress recording
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.CancelRecordResponse> cancel(
        io.github.chirino.memory.grpc.v1.CancelRecordRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCancelMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Check if response recorder is enabled
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.IsEnabledResponse> isEnabled(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getIsEnabledMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Check which conversations have active recordings
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.CheckRecordingsResponse> checkRecordings(
        io.github.chirino.memory.grpc.v1.CheckRecordingsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCheckRecordingsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REPLAY = 0;
  private static final int METHODID_CANCEL = 1;
  private static final int METHODID_IS_ENABLED = 2;
  private static final int METHODID_CHECK_RECORDINGS = 3;
  private static final int METHODID_RECORD = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REPLAY:
          serviceImpl.replay((io.github.chirino.memory.grpc.v1.ReplayRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ReplayResponse>) responseObserver);
          break;
        case METHODID_CANCEL:
          serviceImpl.cancel((io.github.chirino.memory.grpc.v1.CancelRecordRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.CancelRecordResponse>) responseObserver);
          break;
        case METHODID_IS_ENABLED:
          serviceImpl.isEnabled((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.IsEnabledResponse>) responseObserver);
          break;
        case METHODID_CHECK_RECORDINGS:
          serviceImpl.checkRecordings((io.github.chirino.memory.grpc.v1.CheckRecordingsRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.CheckRecordingsResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_RECORD:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.record(
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.RecordResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getRecordMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.RecordRequest,
              io.github.chirino.memory.grpc.v1.RecordResponse>(
                service, METHODID_RECORD)))
        .addMethod(
          getReplayMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.ReplayRequest,
              io.github.chirino.memory.grpc.v1.ReplayResponse>(
                service, METHODID_REPLAY)))
        .addMethod(
          getCancelMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.CancelRecordRequest,
              io.github.chirino.memory.grpc.v1.CancelRecordResponse>(
                service, METHODID_CANCEL)))
        .addMethod(
          getIsEnabledMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.protobuf.Empty,
              io.github.chirino.memory.grpc.v1.IsEnabledResponse>(
                service, METHODID_IS_ENABLED)))
        .addMethod(
          getCheckRecordingsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.CheckRecordingsRequest,
              io.github.chirino.memory.grpc.v1.CheckRecordingsResponse>(
                service, METHODID_CHECK_RECORDINGS)))
        .build();
  }

  private static abstract class ResponseRecorderServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ResponseRecorderServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.github.chirino.memory.grpc.v1.MemoryService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ResponseRecorderService");
    }
  }

  private static final class ResponseRecorderServiceFileDescriptorSupplier
      extends ResponseRecorderServiceBaseDescriptorSupplier {
    ResponseRecorderServiceFileDescriptorSupplier() {}
  }

  private static final class ResponseRecorderServiceMethodDescriptorSupplier
      extends ResponseRecorderServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ResponseRecorderServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ResponseRecorderServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ResponseRecorderServiceFileDescriptorSupplier())
              .addMethod(getRecordMethod())
              .addMethod(getReplayMethod())
              .addMethod(getCancelMethod())
              .addMethod(getIsEnabledMethod())
              .addMethod(getCheckRecordingsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
