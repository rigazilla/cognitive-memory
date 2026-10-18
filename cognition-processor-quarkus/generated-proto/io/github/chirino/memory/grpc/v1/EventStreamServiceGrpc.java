package io.github.chirino.memory.grpc.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: memory/v1/memory_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class EventStreamServiceGrpc {

  private EventStreamServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "memory.v1.EventStreamService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.SubscribeEventsRequest,
      io.github.chirino.memory.grpc.v1.EventNotification> getSubscribeEventsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SubscribeEvents",
      requestType = io.github.chirino.memory.grpc.v1.SubscribeEventsRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.EventNotification.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.SubscribeEventsRequest,
      io.github.chirino.memory.grpc.v1.EventNotification> getSubscribeEventsMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.SubscribeEventsRequest, io.github.chirino.memory.grpc.v1.EventNotification> getSubscribeEventsMethod;
    if ((getSubscribeEventsMethod = EventStreamServiceGrpc.getSubscribeEventsMethod) == null) {
      synchronized (EventStreamServiceGrpc.class) {
        if ((getSubscribeEventsMethod = EventStreamServiceGrpc.getSubscribeEventsMethod) == null) {
          EventStreamServiceGrpc.getSubscribeEventsMethod = getSubscribeEventsMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.SubscribeEventsRequest, io.github.chirino.memory.grpc.v1.EventNotification>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SubscribeEvents"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.SubscribeEventsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.EventNotification.getDefaultInstance()))
              .setSchemaDescriptor(new EventStreamServiceMethodDescriptorSupplier("SubscribeEvents"))
              .build();
        }
      }
    }
    return getSubscribeEventsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EventStreamServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EventStreamServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EventStreamServiceStub>() {
        @java.lang.Override
        public EventStreamServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EventStreamServiceStub(channel, callOptions);
        }
      };
    return EventStreamServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EventStreamServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EventStreamServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EventStreamServiceBlockingStub>() {
        @java.lang.Override
        public EventStreamServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EventStreamServiceBlockingStub(channel, callOptions);
        }
      };
    return EventStreamServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EventStreamServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EventStreamServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EventStreamServiceFutureStub>() {
        @java.lang.Override
        public EventStreamServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EventStreamServiceFutureStub(channel, callOptions);
        }
      };
    return EventStreamServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * Subscribe to real-time events (server streaming).
     * Events are filtered by the caller's conversation group membership.
     * </pre>
     */
    default void subscribeEvents(io.github.chirino.memory.grpc.v1.SubscribeEventsRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.EventNotification> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSubscribeEventsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service EventStreamService.
   */
  public static abstract class EventStreamServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return EventStreamServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service EventStreamService.
   */
  public static final class EventStreamServiceStub
      extends io.grpc.stub.AbstractAsyncStub<EventStreamServiceStub> {
    private EventStreamServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EventStreamServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EventStreamServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Subscribe to real-time events (server streaming).
     * Events are filtered by the caller's conversation group membership.
     * </pre>
     */
    public void subscribeEvents(io.github.chirino.memory.grpc.v1.SubscribeEventsRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.EventNotification> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getSubscribeEventsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service EventStreamService.
   */
  public static final class EventStreamServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<EventStreamServiceBlockingStub> {
    private EventStreamServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EventStreamServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EventStreamServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Subscribe to real-time events (server streaming).
     * Events are filtered by the caller's conversation group membership.
     * </pre>
     */
    public java.util.Iterator<io.github.chirino.memory.grpc.v1.EventNotification> subscribeEvents(
        io.github.chirino.memory.grpc.v1.SubscribeEventsRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getSubscribeEventsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service EventStreamService.
   */
  public static final class EventStreamServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<EventStreamServiceFutureStub> {
    private EventStreamServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EventStreamServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EventStreamServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_SUBSCRIBE_EVENTS = 0;

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
        case METHODID_SUBSCRIBE_EVENTS:
          serviceImpl.subscribeEvents((io.github.chirino.memory.grpc.v1.SubscribeEventsRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.EventNotification>) responseObserver);
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
          getSubscribeEventsMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.SubscribeEventsRequest,
              io.github.chirino.memory.grpc.v1.EventNotification>(
                service, METHODID_SUBSCRIBE_EVENTS)))
        .build();
  }

  private static abstract class EventStreamServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EventStreamServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.github.chirino.memory.grpc.v1.MemoryService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("EventStreamService");
    }
  }

  private static final class EventStreamServiceFileDescriptorSupplier
      extends EventStreamServiceBaseDescriptorSupplier {
    EventStreamServiceFileDescriptorSupplier() {}
  }

  private static final class EventStreamServiceMethodDescriptorSupplier
      extends EventStreamServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    EventStreamServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (EventStreamServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EventStreamServiceFileDescriptorSupplier())
              .addMethod(getSubscribeEventsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
