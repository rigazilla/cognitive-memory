package io.github.chirino.memory.grpc.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: memory/v1/memory_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AttachmentsServiceGrpc {

  private AttachmentsServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "memory.v1.AttachmentsService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.UploadAttachmentRequest,
      io.github.chirino.memory.grpc.v1.UploadAttachmentResponse> getUploadAttachmentMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UploadAttachment",
      requestType = io.github.chirino.memory.grpc.v1.UploadAttachmentRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.UploadAttachmentResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.UploadAttachmentRequest,
      io.github.chirino.memory.grpc.v1.UploadAttachmentResponse> getUploadAttachmentMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.UploadAttachmentRequest, io.github.chirino.memory.grpc.v1.UploadAttachmentResponse> getUploadAttachmentMethod;
    if ((getUploadAttachmentMethod = AttachmentsServiceGrpc.getUploadAttachmentMethod) == null) {
      synchronized (AttachmentsServiceGrpc.class) {
        if ((getUploadAttachmentMethod = AttachmentsServiceGrpc.getUploadAttachmentMethod) == null) {
          AttachmentsServiceGrpc.getUploadAttachmentMethod = getUploadAttachmentMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.UploadAttachmentRequest, io.github.chirino.memory.grpc.v1.UploadAttachmentResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UploadAttachment"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.UploadAttachmentRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.UploadAttachmentResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AttachmentsServiceMethodDescriptorSupplier("UploadAttachment"))
              .build();
        }
      }
    }
    return getUploadAttachmentMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.GetAttachmentRequest,
      io.github.chirino.memory.grpc.v1.AttachmentInfo> getGetAttachmentMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAttachment",
      requestType = io.github.chirino.memory.grpc.v1.GetAttachmentRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.AttachmentInfo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.GetAttachmentRequest,
      io.github.chirino.memory.grpc.v1.AttachmentInfo> getGetAttachmentMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.GetAttachmentRequest, io.github.chirino.memory.grpc.v1.AttachmentInfo> getGetAttachmentMethod;
    if ((getGetAttachmentMethod = AttachmentsServiceGrpc.getGetAttachmentMethod) == null) {
      synchronized (AttachmentsServiceGrpc.class) {
        if ((getGetAttachmentMethod = AttachmentsServiceGrpc.getGetAttachmentMethod) == null) {
          AttachmentsServiceGrpc.getGetAttachmentMethod = getGetAttachmentMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.GetAttachmentRequest, io.github.chirino.memory.grpc.v1.AttachmentInfo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAttachment"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.GetAttachmentRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AttachmentInfo.getDefaultInstance()))
              .setSchemaDescriptor(new AttachmentsServiceMethodDescriptorSupplier("GetAttachment"))
              .build();
        }
      }
    }
    return getGetAttachmentMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.DownloadAttachmentRequest,
      io.github.chirino.memory.grpc.v1.DownloadAttachmentResponse> getDownloadAttachmentMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DownloadAttachment",
      requestType = io.github.chirino.memory.grpc.v1.DownloadAttachmentRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.DownloadAttachmentResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.DownloadAttachmentRequest,
      io.github.chirino.memory.grpc.v1.DownloadAttachmentResponse> getDownloadAttachmentMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.DownloadAttachmentRequest, io.github.chirino.memory.grpc.v1.DownloadAttachmentResponse> getDownloadAttachmentMethod;
    if ((getDownloadAttachmentMethod = AttachmentsServiceGrpc.getDownloadAttachmentMethod) == null) {
      synchronized (AttachmentsServiceGrpc.class) {
        if ((getDownloadAttachmentMethod = AttachmentsServiceGrpc.getDownloadAttachmentMethod) == null) {
          AttachmentsServiceGrpc.getDownloadAttachmentMethod = getDownloadAttachmentMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.DownloadAttachmentRequest, io.github.chirino.memory.grpc.v1.DownloadAttachmentResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DownloadAttachment"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.DownloadAttachmentRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.DownloadAttachmentResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AttachmentsServiceMethodDescriptorSupplier("DownloadAttachment"))
              .build();
        }
      }
    }
    return getDownloadAttachmentMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AttachmentsServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AttachmentsServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AttachmentsServiceStub>() {
        @java.lang.Override
        public AttachmentsServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AttachmentsServiceStub(channel, callOptions);
        }
      };
    return AttachmentsServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AttachmentsServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AttachmentsServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AttachmentsServiceBlockingStub>() {
        @java.lang.Override
        public AttachmentsServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AttachmentsServiceBlockingStub(channel, callOptions);
        }
      };
    return AttachmentsServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AttachmentsServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AttachmentsServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AttachmentsServiceFutureStub>() {
        @java.lang.Override
        public AttachmentsServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AttachmentsServiceFutureStub(channel, callOptions);
        }
      };
    return AttachmentsServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * Upload a file as a stream of chunks. First message must contain metadata.
     * Subsequent messages contain file data chunks.
     * Server responds once the upload is complete or an error occurs.
     * </pre>
     */
    default io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.UploadAttachmentRequest> uploadAttachment(
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.UploadAttachmentResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getUploadAttachmentMethod(), responseObserver);
    }

    /**
     * <pre>
     * Retrieve attachment metadata (not the file content).
     * </pre>
     */
    default void getAttachment(io.github.chirino.memory.grpc.v1.GetAttachmentRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AttachmentInfo> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAttachmentMethod(), responseObserver);
    }

    /**
     * <pre>
     * Download a file as a stream of chunks.
     * </pre>
     */
    default void downloadAttachment(io.github.chirino.memory.grpc.v1.DownloadAttachmentRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.DownloadAttachmentResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDownloadAttachmentMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service AttachmentsService.
   */
  public static abstract class AttachmentsServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return AttachmentsServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service AttachmentsService.
   */
  public static final class AttachmentsServiceStub
      extends io.grpc.stub.AbstractAsyncStub<AttachmentsServiceStub> {
    private AttachmentsServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AttachmentsServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AttachmentsServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Upload a file as a stream of chunks. First message must contain metadata.
     * Subsequent messages contain file data chunks.
     * Server responds once the upload is complete or an error occurs.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.UploadAttachmentRequest> uploadAttachment(
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.UploadAttachmentResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getUploadAttachmentMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * Retrieve attachment metadata (not the file content).
     * </pre>
     */
    public void getAttachment(io.github.chirino.memory.grpc.v1.GetAttachmentRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AttachmentInfo> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAttachmentMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Download a file as a stream of chunks.
     * </pre>
     */
    public void downloadAttachment(io.github.chirino.memory.grpc.v1.DownloadAttachmentRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.DownloadAttachmentResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getDownloadAttachmentMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service AttachmentsService.
   */
  public static final class AttachmentsServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<AttachmentsServiceBlockingStub> {
    private AttachmentsServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AttachmentsServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AttachmentsServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Retrieve attachment metadata (not the file content).
     * </pre>
     */
    public io.github.chirino.memory.grpc.v1.AttachmentInfo getAttachment(io.github.chirino.memory.grpc.v1.GetAttachmentRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAttachmentMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Download a file as a stream of chunks.
     * </pre>
     */
    public java.util.Iterator<io.github.chirino.memory.grpc.v1.DownloadAttachmentResponse> downloadAttachment(
        io.github.chirino.memory.grpc.v1.DownloadAttachmentRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getDownloadAttachmentMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service AttachmentsService.
   */
  public static final class AttachmentsServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<AttachmentsServiceFutureStub> {
    private AttachmentsServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AttachmentsServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AttachmentsServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Retrieve attachment metadata (not the file content).
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.AttachmentInfo> getAttachment(
        io.github.chirino.memory.grpc.v1.GetAttachmentRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAttachmentMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ATTACHMENT = 0;
  private static final int METHODID_DOWNLOAD_ATTACHMENT = 1;
  private static final int METHODID_UPLOAD_ATTACHMENT = 2;

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
        case METHODID_GET_ATTACHMENT:
          serviceImpl.getAttachment((io.github.chirino.memory.grpc.v1.GetAttachmentRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AttachmentInfo>) responseObserver);
          break;
        case METHODID_DOWNLOAD_ATTACHMENT:
          serviceImpl.downloadAttachment((io.github.chirino.memory.grpc.v1.DownloadAttachmentRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.DownloadAttachmentResponse>) responseObserver);
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
        case METHODID_UPLOAD_ATTACHMENT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.uploadAttachment(
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.UploadAttachmentResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getUploadAttachmentMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.UploadAttachmentRequest,
              io.github.chirino.memory.grpc.v1.UploadAttachmentResponse>(
                service, METHODID_UPLOAD_ATTACHMENT)))
        .addMethod(
          getGetAttachmentMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.GetAttachmentRequest,
              io.github.chirino.memory.grpc.v1.AttachmentInfo>(
                service, METHODID_GET_ATTACHMENT)))
        .addMethod(
          getDownloadAttachmentMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.DownloadAttachmentRequest,
              io.github.chirino.memory.grpc.v1.DownloadAttachmentResponse>(
                service, METHODID_DOWNLOAD_ATTACHMENT)))
        .build();
  }

  private static abstract class AttachmentsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AttachmentsServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.github.chirino.memory.grpc.v1.MemoryService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AttachmentsService");
    }
  }

  private static final class AttachmentsServiceFileDescriptorSupplier
      extends AttachmentsServiceBaseDescriptorSupplier {
    AttachmentsServiceFileDescriptorSupplier() {}
  }

  private static final class AttachmentsServiceMethodDescriptorSupplier
      extends AttachmentsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    AttachmentsServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (AttachmentsServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AttachmentsServiceFileDescriptorSupplier())
              .addMethod(getUploadAttachmentMethod())
              .addMethod(getGetAttachmentMethod())
              .addMethod(getDownloadAttachmentMethod())
              .build();
        }
      }
    }
    return result;
  }
}
