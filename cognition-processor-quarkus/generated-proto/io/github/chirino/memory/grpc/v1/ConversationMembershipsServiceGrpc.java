package io.github.chirino.memory.grpc.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: memory/v1/memory_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ConversationMembershipsServiceGrpc {

  private ConversationMembershipsServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "memory.v1.ConversationMembershipsService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListMembershipsRequest,
      io.github.chirino.memory.grpc.v1.ListMembershipsResponse> getListMembershipsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListMemberships",
      requestType = io.github.chirino.memory.grpc.v1.ListMembershipsRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.ListMembershipsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListMembershipsRequest,
      io.github.chirino.memory.grpc.v1.ListMembershipsResponse> getListMembershipsMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListMembershipsRequest, io.github.chirino.memory.grpc.v1.ListMembershipsResponse> getListMembershipsMethod;
    if ((getListMembershipsMethod = ConversationMembershipsServiceGrpc.getListMembershipsMethod) == null) {
      synchronized (ConversationMembershipsServiceGrpc.class) {
        if ((getListMembershipsMethod = ConversationMembershipsServiceGrpc.getListMembershipsMethod) == null) {
          ConversationMembershipsServiceGrpc.getListMembershipsMethod = getListMembershipsMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.ListMembershipsRequest, io.github.chirino.memory.grpc.v1.ListMembershipsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListMemberships"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ListMembershipsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ListMembershipsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ConversationMembershipsServiceMethodDescriptorSupplier("ListMemberships"))
              .build();
        }
      }
    }
    return getListMembershipsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ShareConversationRequest,
      io.github.chirino.memory.grpc.v1.ConversationMembership> getShareConversationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ShareConversation",
      requestType = io.github.chirino.memory.grpc.v1.ShareConversationRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.ConversationMembership.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ShareConversationRequest,
      io.github.chirino.memory.grpc.v1.ConversationMembership> getShareConversationMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ShareConversationRequest, io.github.chirino.memory.grpc.v1.ConversationMembership> getShareConversationMethod;
    if ((getShareConversationMethod = ConversationMembershipsServiceGrpc.getShareConversationMethod) == null) {
      synchronized (ConversationMembershipsServiceGrpc.class) {
        if ((getShareConversationMethod = ConversationMembershipsServiceGrpc.getShareConversationMethod) == null) {
          ConversationMembershipsServiceGrpc.getShareConversationMethod = getShareConversationMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.ShareConversationRequest, io.github.chirino.memory.grpc.v1.ConversationMembership>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ShareConversation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ShareConversationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ConversationMembership.getDefaultInstance()))
              .setSchemaDescriptor(new ConversationMembershipsServiceMethodDescriptorSupplier("ShareConversation"))
              .build();
        }
      }
    }
    return getShareConversationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.UpdateMembershipRequest,
      io.github.chirino.memory.grpc.v1.ConversationMembership> getUpdateMembershipMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateMembership",
      requestType = io.github.chirino.memory.grpc.v1.UpdateMembershipRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.ConversationMembership.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.UpdateMembershipRequest,
      io.github.chirino.memory.grpc.v1.ConversationMembership> getUpdateMembershipMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.UpdateMembershipRequest, io.github.chirino.memory.grpc.v1.ConversationMembership> getUpdateMembershipMethod;
    if ((getUpdateMembershipMethod = ConversationMembershipsServiceGrpc.getUpdateMembershipMethod) == null) {
      synchronized (ConversationMembershipsServiceGrpc.class) {
        if ((getUpdateMembershipMethod = ConversationMembershipsServiceGrpc.getUpdateMembershipMethod) == null) {
          ConversationMembershipsServiceGrpc.getUpdateMembershipMethod = getUpdateMembershipMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.UpdateMembershipRequest, io.github.chirino.memory.grpc.v1.ConversationMembership>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateMembership"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.UpdateMembershipRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ConversationMembership.getDefaultInstance()))
              .setSchemaDescriptor(new ConversationMembershipsServiceMethodDescriptorSupplier("UpdateMembership"))
              .build();
        }
      }
    }
    return getUpdateMembershipMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.DeleteMembershipRequest,
      com.google.protobuf.Empty> getDeleteMembershipMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteMembership",
      requestType = io.github.chirino.memory.grpc.v1.DeleteMembershipRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.DeleteMembershipRequest,
      com.google.protobuf.Empty> getDeleteMembershipMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.DeleteMembershipRequest, com.google.protobuf.Empty> getDeleteMembershipMethod;
    if ((getDeleteMembershipMethod = ConversationMembershipsServiceGrpc.getDeleteMembershipMethod) == null) {
      synchronized (ConversationMembershipsServiceGrpc.class) {
        if ((getDeleteMembershipMethod = ConversationMembershipsServiceGrpc.getDeleteMembershipMethod) == null) {
          ConversationMembershipsServiceGrpc.getDeleteMembershipMethod = getDeleteMembershipMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.DeleteMembershipRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteMembership"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.DeleteMembershipRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ConversationMembershipsServiceMethodDescriptorSupplier("DeleteMembership"))
              .build();
        }
      }
    }
    return getDeleteMembershipMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ConversationMembershipsServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConversationMembershipsServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConversationMembershipsServiceStub>() {
        @java.lang.Override
        public ConversationMembershipsServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConversationMembershipsServiceStub(channel, callOptions);
        }
      };
    return ConversationMembershipsServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ConversationMembershipsServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConversationMembershipsServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConversationMembershipsServiceBlockingStub>() {
        @java.lang.Override
        public ConversationMembershipsServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConversationMembershipsServiceBlockingStub(channel, callOptions);
        }
      };
    return ConversationMembershipsServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ConversationMembershipsServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConversationMembershipsServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConversationMembershipsServiceFutureStub>() {
        @java.lang.Override
        public ConversationMembershipsServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConversationMembershipsServiceFutureStub(channel, callOptions);
        }
      };
    return ConversationMembershipsServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void listMemberships(io.github.chirino.memory.grpc.v1.ListMembershipsRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListMembershipsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListMembershipsMethod(), responseObserver);
    }

    /**
     */
    default void shareConversation(io.github.chirino.memory.grpc.v1.ShareConversationRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ConversationMembership> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getShareConversationMethod(), responseObserver);
    }

    /**
     */
    default void updateMembership(io.github.chirino.memory.grpc.v1.UpdateMembershipRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ConversationMembership> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateMembershipMethod(), responseObserver);
    }

    /**
     */
    default void deleteMembership(io.github.chirino.memory.grpc.v1.DeleteMembershipRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteMembershipMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ConversationMembershipsService.
   */
  public static abstract class ConversationMembershipsServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ConversationMembershipsServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ConversationMembershipsService.
   */
  public static final class ConversationMembershipsServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ConversationMembershipsServiceStub> {
    private ConversationMembershipsServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConversationMembershipsServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConversationMembershipsServiceStub(channel, callOptions);
    }

    /**
     */
    public void listMemberships(io.github.chirino.memory.grpc.v1.ListMembershipsRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListMembershipsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListMembershipsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void shareConversation(io.github.chirino.memory.grpc.v1.ShareConversationRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ConversationMembership> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getShareConversationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateMembership(io.github.chirino.memory.grpc.v1.UpdateMembershipRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ConversationMembership> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateMembershipMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteMembership(io.github.chirino.memory.grpc.v1.DeleteMembershipRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteMembershipMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ConversationMembershipsService.
   */
  public static final class ConversationMembershipsServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ConversationMembershipsServiceBlockingStub> {
    private ConversationMembershipsServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConversationMembershipsServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConversationMembershipsServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.ListMembershipsResponse listMemberships(io.github.chirino.memory.grpc.v1.ListMembershipsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListMembershipsMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.ConversationMembership shareConversation(io.github.chirino.memory.grpc.v1.ShareConversationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getShareConversationMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.ConversationMembership updateMembership(io.github.chirino.memory.grpc.v1.UpdateMembershipRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateMembershipMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty deleteMembership(io.github.chirino.memory.grpc.v1.DeleteMembershipRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteMembershipMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ConversationMembershipsService.
   */
  public static final class ConversationMembershipsServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ConversationMembershipsServiceFutureStub> {
    private ConversationMembershipsServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConversationMembershipsServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConversationMembershipsServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.ListMembershipsResponse> listMemberships(
        io.github.chirino.memory.grpc.v1.ListMembershipsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListMembershipsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.ConversationMembership> shareConversation(
        io.github.chirino.memory.grpc.v1.ShareConversationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getShareConversationMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.ConversationMembership> updateMembership(
        io.github.chirino.memory.grpc.v1.UpdateMembershipRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateMembershipMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> deleteMembership(
        io.github.chirino.memory.grpc.v1.DeleteMembershipRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteMembershipMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST_MEMBERSHIPS = 0;
  private static final int METHODID_SHARE_CONVERSATION = 1;
  private static final int METHODID_UPDATE_MEMBERSHIP = 2;
  private static final int METHODID_DELETE_MEMBERSHIP = 3;

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
        case METHODID_LIST_MEMBERSHIPS:
          serviceImpl.listMemberships((io.github.chirino.memory.grpc.v1.ListMembershipsRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListMembershipsResponse>) responseObserver);
          break;
        case METHODID_SHARE_CONVERSATION:
          serviceImpl.shareConversation((io.github.chirino.memory.grpc.v1.ShareConversationRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ConversationMembership>) responseObserver);
          break;
        case METHODID_UPDATE_MEMBERSHIP:
          serviceImpl.updateMembership((io.github.chirino.memory.grpc.v1.UpdateMembershipRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ConversationMembership>) responseObserver);
          break;
        case METHODID_DELETE_MEMBERSHIP:
          serviceImpl.deleteMembership((io.github.chirino.memory.grpc.v1.DeleteMembershipRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
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
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getListMembershipsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.ListMembershipsRequest,
              io.github.chirino.memory.grpc.v1.ListMembershipsResponse>(
                service, METHODID_LIST_MEMBERSHIPS)))
        .addMethod(
          getShareConversationMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.ShareConversationRequest,
              io.github.chirino.memory.grpc.v1.ConversationMembership>(
                service, METHODID_SHARE_CONVERSATION)))
        .addMethod(
          getUpdateMembershipMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.UpdateMembershipRequest,
              io.github.chirino.memory.grpc.v1.ConversationMembership>(
                service, METHODID_UPDATE_MEMBERSHIP)))
        .addMethod(
          getDeleteMembershipMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.DeleteMembershipRequest,
              com.google.protobuf.Empty>(
                service, METHODID_DELETE_MEMBERSHIP)))
        .build();
  }

  private static abstract class ConversationMembershipsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ConversationMembershipsServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.github.chirino.memory.grpc.v1.MemoryService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ConversationMembershipsService");
    }
  }

  private static final class ConversationMembershipsServiceFileDescriptorSupplier
      extends ConversationMembershipsServiceBaseDescriptorSupplier {
    ConversationMembershipsServiceFileDescriptorSupplier() {}
  }

  private static final class ConversationMembershipsServiceMethodDescriptorSupplier
      extends ConversationMembershipsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ConversationMembershipsServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (ConversationMembershipsServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ConversationMembershipsServiceFileDescriptorSupplier())
              .addMethod(getListMembershipsMethod())
              .addMethod(getShareConversationMethod())
              .addMethod(getUpdateMembershipMethod())
              .addMethod(getDeleteMembershipMethod())
              .build();
        }
      }
    }
    return result;
  }
}
