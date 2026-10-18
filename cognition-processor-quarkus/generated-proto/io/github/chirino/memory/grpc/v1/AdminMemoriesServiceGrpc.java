package io.github.chirino.memory.grpc.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: memory/v1/memory_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AdminMemoriesServiceGrpc {

  private AdminMemoriesServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "memory.v1.AdminMemoriesService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListMemoriesRequest,
      io.github.chirino.memory.grpc.v1.AdminListMemoriesResponse> getListMemoriesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListMemories",
      requestType = io.github.chirino.memory.grpc.v1.AdminListMemoriesRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.AdminListMemoriesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListMemoriesRequest,
      io.github.chirino.memory.grpc.v1.AdminListMemoriesResponse> getListMemoriesMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListMemoriesRequest, io.github.chirino.memory.grpc.v1.AdminListMemoriesResponse> getListMemoriesMethod;
    if ((getListMemoriesMethod = AdminMemoriesServiceGrpc.getListMemoriesMethod) == null) {
      synchronized (AdminMemoriesServiceGrpc.class) {
        if ((getListMemoriesMethod = AdminMemoriesServiceGrpc.getListMemoriesMethod) == null) {
          AdminMemoriesServiceGrpc.getListMemoriesMethod = getListMemoriesMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.AdminListMemoriesRequest, io.github.chirino.memory.grpc.v1.AdminListMemoriesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListMemories"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminListMemoriesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminListMemoriesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AdminMemoriesServiceMethodDescriptorSupplier("ListMemories"))
              .build();
        }
      }
    }
    return getListMemoriesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminGetMemoryRequest,
      io.github.chirino.memory.grpc.v1.AdminMemoryItem> getGetMemoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMemory",
      requestType = io.github.chirino.memory.grpc.v1.AdminGetMemoryRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.AdminMemoryItem.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminGetMemoryRequest,
      io.github.chirino.memory.grpc.v1.AdminMemoryItem> getGetMemoryMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminGetMemoryRequest, io.github.chirino.memory.grpc.v1.AdminMemoryItem> getGetMemoryMethod;
    if ((getGetMemoryMethod = AdminMemoriesServiceGrpc.getGetMemoryMethod) == null) {
      synchronized (AdminMemoriesServiceGrpc.class) {
        if ((getGetMemoryMethod = AdminMemoriesServiceGrpc.getGetMemoryMethod) == null) {
          AdminMemoriesServiceGrpc.getGetMemoryMethod = getGetMemoryMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.AdminGetMemoryRequest, io.github.chirino.memory.grpc.v1.AdminMemoryItem>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMemory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminGetMemoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminMemoryItem.getDefaultInstance()))
              .setSchemaDescriptor(new AdminMemoriesServiceMethodDescriptorSupplier("GetMemory"))
              .build();
        }
      }
    }
    return getGetMemoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminSearchMemoriesRequest,
      io.github.chirino.memory.grpc.v1.AdminSearchMemoriesResponse> getSearchMemoriesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SearchMemories",
      requestType = io.github.chirino.memory.grpc.v1.AdminSearchMemoriesRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.AdminSearchMemoriesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminSearchMemoriesRequest,
      io.github.chirino.memory.grpc.v1.AdminSearchMemoriesResponse> getSearchMemoriesMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminSearchMemoriesRequest, io.github.chirino.memory.grpc.v1.AdminSearchMemoriesResponse> getSearchMemoriesMethod;
    if ((getSearchMemoriesMethod = AdminMemoriesServiceGrpc.getSearchMemoriesMethod) == null) {
      synchronized (AdminMemoriesServiceGrpc.class) {
        if ((getSearchMemoriesMethod = AdminMemoriesServiceGrpc.getSearchMemoriesMethod) == null) {
          AdminMemoriesServiceGrpc.getSearchMemoriesMethod = getSearchMemoriesMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.AdminSearchMemoriesRequest, io.github.chirino.memory.grpc.v1.AdminSearchMemoriesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SearchMemories"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminSearchMemoriesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminSearchMemoriesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AdminMemoriesServiceMethodDescriptorSupplier("SearchMemories"))
              .build();
        }
      }
    }
    return getSearchMemoriesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesRequest,
      io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesResponse> getListNamespacesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListNamespaces",
      requestType = io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesRequest,
      io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesResponse> getListNamespacesMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesRequest, io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesResponse> getListNamespacesMethod;
    if ((getListNamespacesMethod = AdminMemoriesServiceGrpc.getListNamespacesMethod) == null) {
      synchronized (AdminMemoriesServiceGrpc.class) {
        if ((getListNamespacesMethod = AdminMemoriesServiceGrpc.getListNamespacesMethod) == null) {
          AdminMemoriesServiceGrpc.getListNamespacesMethod = getListNamespacesMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesRequest, io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListNamespaces"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AdminMemoriesServiceMethodDescriptorSupplier("ListNamespaces"))
              .build();
        }
      }
    }
    return getListNamespacesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminDeleteMemoryRequest,
      com.google.protobuf.Empty> getDeleteMemoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteMemory",
      requestType = io.github.chirino.memory.grpc.v1.AdminDeleteMemoryRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminDeleteMemoryRequest,
      com.google.protobuf.Empty> getDeleteMemoryMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminDeleteMemoryRequest, com.google.protobuf.Empty> getDeleteMemoryMethod;
    if ((getDeleteMemoryMethod = AdminMemoriesServiceGrpc.getDeleteMemoryMethod) == null) {
      synchronized (AdminMemoriesServiceGrpc.class) {
        if ((getDeleteMemoryMethod = AdminMemoriesServiceGrpc.getDeleteMemoryMethod) == null) {
          AdminMemoriesServiceGrpc.getDeleteMemoryMethod = getDeleteMemoryMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.AdminDeleteMemoryRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteMemory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminDeleteMemoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new AdminMemoriesServiceMethodDescriptorSupplier("DeleteMemory"))
              .build();
        }
      }
    }
    return getDeleteMemoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminGetMemoryUsageRequest,
      io.github.chirino.memory.grpc.v1.MemoryUsage> getGetMemoryUsageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMemoryUsage",
      requestType = io.github.chirino.memory.grpc.v1.AdminGetMemoryUsageRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.MemoryUsage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminGetMemoryUsageRequest,
      io.github.chirino.memory.grpc.v1.MemoryUsage> getGetMemoryUsageMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminGetMemoryUsageRequest, io.github.chirino.memory.grpc.v1.MemoryUsage> getGetMemoryUsageMethod;
    if ((getGetMemoryUsageMethod = AdminMemoriesServiceGrpc.getGetMemoryUsageMethod) == null) {
      synchronized (AdminMemoriesServiceGrpc.class) {
        if ((getGetMemoryUsageMethod = AdminMemoriesServiceGrpc.getGetMemoryUsageMethod) == null) {
          AdminMemoriesServiceGrpc.getGetMemoryUsageMethod = getGetMemoryUsageMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.AdminGetMemoryUsageRequest, io.github.chirino.memory.grpc.v1.MemoryUsage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMemoryUsage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminGetMemoryUsageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.MemoryUsage.getDefaultInstance()))
              .setSchemaDescriptor(new AdminMemoriesServiceMethodDescriptorSupplier("GetMemoryUsage"))
              .build();
        }
      }
    }
    return getGetMemoryUsageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListTopMemoryUsageRequest,
      io.github.chirino.memory.grpc.v1.ListTopMemoryUsageResponse> getListTopMemoryUsageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListTopMemoryUsage",
      requestType = io.github.chirino.memory.grpc.v1.AdminListTopMemoryUsageRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.ListTopMemoryUsageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListTopMemoryUsageRequest,
      io.github.chirino.memory.grpc.v1.ListTopMemoryUsageResponse> getListTopMemoryUsageMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListTopMemoryUsageRequest, io.github.chirino.memory.grpc.v1.ListTopMemoryUsageResponse> getListTopMemoryUsageMethod;
    if ((getListTopMemoryUsageMethod = AdminMemoriesServiceGrpc.getListTopMemoryUsageMethod) == null) {
      synchronized (AdminMemoriesServiceGrpc.class) {
        if ((getListTopMemoryUsageMethod = AdminMemoriesServiceGrpc.getListTopMemoryUsageMethod) == null) {
          AdminMemoriesServiceGrpc.getListTopMemoryUsageMethod = getListTopMemoryUsageMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.AdminListTopMemoryUsageRequest, io.github.chirino.memory.grpc.v1.ListTopMemoryUsageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListTopMemoryUsage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminListTopMemoryUsageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ListTopMemoryUsageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AdminMemoriesServiceMethodDescriptorSupplier("ListTopMemoryUsage"))
              .build();
        }
      }
    }
    return getListTopMemoryUsageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminGetMemoryIndexStatusRequest,
      io.github.chirino.memory.grpc.v1.MemoryIndexStatusResponse> getGetMemoryIndexStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMemoryIndexStatus",
      requestType = io.github.chirino.memory.grpc.v1.AdminGetMemoryIndexStatusRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.MemoryIndexStatusResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminGetMemoryIndexStatusRequest,
      io.github.chirino.memory.grpc.v1.MemoryIndexStatusResponse> getGetMemoryIndexStatusMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminGetMemoryIndexStatusRequest, io.github.chirino.memory.grpc.v1.MemoryIndexStatusResponse> getGetMemoryIndexStatusMethod;
    if ((getGetMemoryIndexStatusMethod = AdminMemoriesServiceGrpc.getGetMemoryIndexStatusMethod) == null) {
      synchronized (AdminMemoriesServiceGrpc.class) {
        if ((getGetMemoryIndexStatusMethod = AdminMemoriesServiceGrpc.getGetMemoryIndexStatusMethod) == null) {
          AdminMemoriesServiceGrpc.getGetMemoryIndexStatusMethod = getGetMemoryIndexStatusMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.AdminGetMemoryIndexStatusRequest, io.github.chirino.memory.grpc.v1.MemoryIndexStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMemoryIndexStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminGetMemoryIndexStatusRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.MemoryIndexStatusResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AdminMemoriesServiceMethodDescriptorSupplier("GetMemoryIndexStatus"))
              .build();
        }
      }
    }
    return getGetMemoryIndexStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminPutMemoryRequest,
      io.github.chirino.memory.grpc.v1.MemoryWriteResult> getPutMemoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PutMemory",
      requestType = io.github.chirino.memory.grpc.v1.AdminPutMemoryRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.MemoryWriteResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminPutMemoryRequest,
      io.github.chirino.memory.grpc.v1.MemoryWriteResult> getPutMemoryMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminPutMemoryRequest, io.github.chirino.memory.grpc.v1.MemoryWriteResult> getPutMemoryMethod;
    if ((getPutMemoryMethod = AdminMemoriesServiceGrpc.getPutMemoryMethod) == null) {
      synchronized (AdminMemoriesServiceGrpc.class) {
        if ((getPutMemoryMethod = AdminMemoriesServiceGrpc.getPutMemoryMethod) == null) {
          AdminMemoriesServiceGrpc.getPutMemoryMethod = getPutMemoryMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.AdminPutMemoryRequest, io.github.chirino.memory.grpc.v1.MemoryWriteResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PutMemory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminPutMemoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.MemoryWriteResult.getDefaultInstance()))
              .setSchemaDescriptor(new AdminMemoriesServiceMethodDescriptorSupplier("PutMemory"))
              .build();
        }
      }
    }
    return getPutMemoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminUpdateMemoryRequest,
      com.google.protobuf.Empty> getUpdateMemoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateMemory",
      requestType = io.github.chirino.memory.grpc.v1.AdminUpdateMemoryRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminUpdateMemoryRequest,
      com.google.protobuf.Empty> getUpdateMemoryMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminUpdateMemoryRequest, com.google.protobuf.Empty> getUpdateMemoryMethod;
    if ((getUpdateMemoryMethod = AdminMemoriesServiceGrpc.getUpdateMemoryMethod) == null) {
      synchronized (AdminMemoriesServiceGrpc.class) {
        if ((getUpdateMemoryMethod = AdminMemoriesServiceGrpc.getUpdateMemoryMethod) == null) {
          AdminMemoriesServiceGrpc.getUpdateMemoryMethod = getUpdateMemoryMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.AdminUpdateMemoryRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateMemory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminUpdateMemoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new AdminMemoriesServiceMethodDescriptorSupplier("UpdateMemory"))
              .build();
        }
      }
    }
    return getUpdateMemoryMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AdminMemoriesServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AdminMemoriesServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AdminMemoriesServiceStub>() {
        @java.lang.Override
        public AdminMemoriesServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AdminMemoriesServiceStub(channel, callOptions);
        }
      };
    return AdminMemoriesServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AdminMemoriesServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AdminMemoriesServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AdminMemoriesServiceBlockingStub>() {
        @java.lang.Override
        public AdminMemoriesServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AdminMemoriesServiceBlockingStub(channel, callOptions);
        }
      };
    return AdminMemoriesServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AdminMemoriesServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AdminMemoriesServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AdminMemoriesServiceFutureStub>() {
        @java.lang.Override
        public AdminMemoriesServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AdminMemoriesServiceFutureStub(channel, callOptions);
        }
      };
    return AdminMemoriesServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void listMemories(io.github.chirino.memory.grpc.v1.AdminListMemoriesRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminListMemoriesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListMemoriesMethod(), responseObserver);
    }

    /**
     */
    default void getMemory(io.github.chirino.memory.grpc.v1.AdminGetMemoryRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminMemoryItem> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMemoryMethod(), responseObserver);
    }

    /**
     */
    default void searchMemories(io.github.chirino.memory.grpc.v1.AdminSearchMemoriesRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminSearchMemoriesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSearchMemoriesMethod(), responseObserver);
    }

    /**
     */
    default void listNamespaces(io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListNamespacesMethod(), responseObserver);
    }

    /**
     */
    default void deleteMemory(io.github.chirino.memory.grpc.v1.AdminDeleteMemoryRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteMemoryMethod(), responseObserver);
    }

    /**
     */
    default void getMemoryUsage(io.github.chirino.memory.grpc.v1.AdminGetMemoryUsageRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.MemoryUsage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMemoryUsageMethod(), responseObserver);
    }

    /**
     */
    default void listTopMemoryUsage(io.github.chirino.memory.grpc.v1.AdminListTopMemoryUsageRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListTopMemoryUsageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListTopMemoryUsageMethod(), responseObserver);
    }

    /**
     */
    default void getMemoryIndexStatus(io.github.chirino.memory.grpc.v1.AdminGetMemoryIndexStatusRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.MemoryIndexStatusResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMemoryIndexStatusMethod(), responseObserver);
    }

    /**
     */
    default void putMemory(io.github.chirino.memory.grpc.v1.AdminPutMemoryRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.MemoryWriteResult> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPutMemoryMethod(), responseObserver);
    }

    /**
     */
    default void updateMemory(io.github.chirino.memory.grpc.v1.AdminUpdateMemoryRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateMemoryMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service AdminMemoriesService.
   */
  public static abstract class AdminMemoriesServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return AdminMemoriesServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service AdminMemoriesService.
   */
  public static final class AdminMemoriesServiceStub
      extends io.grpc.stub.AbstractAsyncStub<AdminMemoriesServiceStub> {
    private AdminMemoriesServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdminMemoriesServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AdminMemoriesServiceStub(channel, callOptions);
    }

    /**
     */
    public void listMemories(io.github.chirino.memory.grpc.v1.AdminListMemoriesRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminListMemoriesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListMemoriesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getMemory(io.github.chirino.memory.grpc.v1.AdminGetMemoryRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminMemoryItem> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMemoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void searchMemories(io.github.chirino.memory.grpc.v1.AdminSearchMemoriesRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminSearchMemoriesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSearchMemoriesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listNamespaces(io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListNamespacesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteMemory(io.github.chirino.memory.grpc.v1.AdminDeleteMemoryRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteMemoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getMemoryUsage(io.github.chirino.memory.grpc.v1.AdminGetMemoryUsageRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.MemoryUsage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMemoryUsageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listTopMemoryUsage(io.github.chirino.memory.grpc.v1.AdminListTopMemoryUsageRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListTopMemoryUsageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListTopMemoryUsageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getMemoryIndexStatus(io.github.chirino.memory.grpc.v1.AdminGetMemoryIndexStatusRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.MemoryIndexStatusResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMemoryIndexStatusMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void putMemory(io.github.chirino.memory.grpc.v1.AdminPutMemoryRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.MemoryWriteResult> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPutMemoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateMemory(io.github.chirino.memory.grpc.v1.AdminUpdateMemoryRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateMemoryMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service AdminMemoriesService.
   */
  public static final class AdminMemoriesServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<AdminMemoriesServiceBlockingStub> {
    private AdminMemoriesServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdminMemoriesServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AdminMemoriesServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.AdminListMemoriesResponse listMemories(io.github.chirino.memory.grpc.v1.AdminListMemoriesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListMemoriesMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.AdminMemoryItem getMemory(io.github.chirino.memory.grpc.v1.AdminGetMemoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMemoryMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.AdminSearchMemoriesResponse searchMemories(io.github.chirino.memory.grpc.v1.AdminSearchMemoriesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSearchMemoriesMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesResponse listNamespaces(io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListNamespacesMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty deleteMemory(io.github.chirino.memory.grpc.v1.AdminDeleteMemoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteMemoryMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.MemoryUsage getMemoryUsage(io.github.chirino.memory.grpc.v1.AdminGetMemoryUsageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMemoryUsageMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.ListTopMemoryUsageResponse listTopMemoryUsage(io.github.chirino.memory.grpc.v1.AdminListTopMemoryUsageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListTopMemoryUsageMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.MemoryIndexStatusResponse getMemoryIndexStatus(io.github.chirino.memory.grpc.v1.AdminGetMemoryIndexStatusRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMemoryIndexStatusMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.MemoryWriteResult putMemory(io.github.chirino.memory.grpc.v1.AdminPutMemoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPutMemoryMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty updateMemory(io.github.chirino.memory.grpc.v1.AdminUpdateMemoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateMemoryMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service AdminMemoriesService.
   */
  public static final class AdminMemoriesServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<AdminMemoriesServiceFutureStub> {
    private AdminMemoriesServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdminMemoriesServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AdminMemoriesServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.AdminListMemoriesResponse> listMemories(
        io.github.chirino.memory.grpc.v1.AdminListMemoriesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListMemoriesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.AdminMemoryItem> getMemory(
        io.github.chirino.memory.grpc.v1.AdminGetMemoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMemoryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.AdminSearchMemoriesResponse> searchMemories(
        io.github.chirino.memory.grpc.v1.AdminSearchMemoriesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSearchMemoriesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesResponse> listNamespaces(
        io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListNamespacesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> deleteMemory(
        io.github.chirino.memory.grpc.v1.AdminDeleteMemoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteMemoryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.MemoryUsage> getMemoryUsage(
        io.github.chirino.memory.grpc.v1.AdminGetMemoryUsageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMemoryUsageMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.ListTopMemoryUsageResponse> listTopMemoryUsage(
        io.github.chirino.memory.grpc.v1.AdminListTopMemoryUsageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListTopMemoryUsageMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.MemoryIndexStatusResponse> getMemoryIndexStatus(
        io.github.chirino.memory.grpc.v1.AdminGetMemoryIndexStatusRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMemoryIndexStatusMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.MemoryWriteResult> putMemory(
        io.github.chirino.memory.grpc.v1.AdminPutMemoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPutMemoryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> updateMemory(
        io.github.chirino.memory.grpc.v1.AdminUpdateMemoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateMemoryMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST_MEMORIES = 0;
  private static final int METHODID_GET_MEMORY = 1;
  private static final int METHODID_SEARCH_MEMORIES = 2;
  private static final int METHODID_LIST_NAMESPACES = 3;
  private static final int METHODID_DELETE_MEMORY = 4;
  private static final int METHODID_GET_MEMORY_USAGE = 5;
  private static final int METHODID_LIST_TOP_MEMORY_USAGE = 6;
  private static final int METHODID_GET_MEMORY_INDEX_STATUS = 7;
  private static final int METHODID_PUT_MEMORY = 8;
  private static final int METHODID_UPDATE_MEMORY = 9;

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
        case METHODID_LIST_MEMORIES:
          serviceImpl.listMemories((io.github.chirino.memory.grpc.v1.AdminListMemoriesRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminListMemoriesResponse>) responseObserver);
          break;
        case METHODID_GET_MEMORY:
          serviceImpl.getMemory((io.github.chirino.memory.grpc.v1.AdminGetMemoryRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminMemoryItem>) responseObserver);
          break;
        case METHODID_SEARCH_MEMORIES:
          serviceImpl.searchMemories((io.github.chirino.memory.grpc.v1.AdminSearchMemoriesRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminSearchMemoriesResponse>) responseObserver);
          break;
        case METHODID_LIST_NAMESPACES:
          serviceImpl.listNamespaces((io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesResponse>) responseObserver);
          break;
        case METHODID_DELETE_MEMORY:
          serviceImpl.deleteMemory((io.github.chirino.memory.grpc.v1.AdminDeleteMemoryRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_GET_MEMORY_USAGE:
          serviceImpl.getMemoryUsage((io.github.chirino.memory.grpc.v1.AdminGetMemoryUsageRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.MemoryUsage>) responseObserver);
          break;
        case METHODID_LIST_TOP_MEMORY_USAGE:
          serviceImpl.listTopMemoryUsage((io.github.chirino.memory.grpc.v1.AdminListTopMemoryUsageRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListTopMemoryUsageResponse>) responseObserver);
          break;
        case METHODID_GET_MEMORY_INDEX_STATUS:
          serviceImpl.getMemoryIndexStatus((io.github.chirino.memory.grpc.v1.AdminGetMemoryIndexStatusRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.MemoryIndexStatusResponse>) responseObserver);
          break;
        case METHODID_PUT_MEMORY:
          serviceImpl.putMemory((io.github.chirino.memory.grpc.v1.AdminPutMemoryRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.MemoryWriteResult>) responseObserver);
          break;
        case METHODID_UPDATE_MEMORY:
          serviceImpl.updateMemory((io.github.chirino.memory.grpc.v1.AdminUpdateMemoryRequest) request,
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
          getListMemoriesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.AdminListMemoriesRequest,
              io.github.chirino.memory.grpc.v1.AdminListMemoriesResponse>(
                service, METHODID_LIST_MEMORIES)))
        .addMethod(
          getGetMemoryMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.AdminGetMemoryRequest,
              io.github.chirino.memory.grpc.v1.AdminMemoryItem>(
                service, METHODID_GET_MEMORY)))
        .addMethod(
          getSearchMemoriesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.AdminSearchMemoriesRequest,
              io.github.chirino.memory.grpc.v1.AdminSearchMemoriesResponse>(
                service, METHODID_SEARCH_MEMORIES)))
        .addMethod(
          getListNamespacesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesRequest,
              io.github.chirino.memory.grpc.v1.AdminListMemoryNamespacesResponse>(
                service, METHODID_LIST_NAMESPACES)))
        .addMethod(
          getDeleteMemoryMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.AdminDeleteMemoryRequest,
              com.google.protobuf.Empty>(
                service, METHODID_DELETE_MEMORY)))
        .addMethod(
          getGetMemoryUsageMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.AdminGetMemoryUsageRequest,
              io.github.chirino.memory.grpc.v1.MemoryUsage>(
                service, METHODID_GET_MEMORY_USAGE)))
        .addMethod(
          getListTopMemoryUsageMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.AdminListTopMemoryUsageRequest,
              io.github.chirino.memory.grpc.v1.ListTopMemoryUsageResponse>(
                service, METHODID_LIST_TOP_MEMORY_USAGE)))
        .addMethod(
          getGetMemoryIndexStatusMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.AdminGetMemoryIndexStatusRequest,
              io.github.chirino.memory.grpc.v1.MemoryIndexStatusResponse>(
                service, METHODID_GET_MEMORY_INDEX_STATUS)))
        .addMethod(
          getPutMemoryMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.AdminPutMemoryRequest,
              io.github.chirino.memory.grpc.v1.MemoryWriteResult>(
                service, METHODID_PUT_MEMORY)))
        .addMethod(
          getUpdateMemoryMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.AdminUpdateMemoryRequest,
              com.google.protobuf.Empty>(
                service, METHODID_UPDATE_MEMORY)))
        .build();
  }

  private static abstract class AdminMemoriesServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AdminMemoriesServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.github.chirino.memory.grpc.v1.MemoryService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AdminMemoriesService");
    }
  }

  private static final class AdminMemoriesServiceFileDescriptorSupplier
      extends AdminMemoriesServiceBaseDescriptorSupplier {
    AdminMemoriesServiceFileDescriptorSupplier() {}
  }

  private static final class AdminMemoriesServiceMethodDescriptorSupplier
      extends AdminMemoriesServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    AdminMemoriesServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (AdminMemoriesServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AdminMemoriesServiceFileDescriptorSupplier())
              .addMethod(getListMemoriesMethod())
              .addMethod(getGetMemoryMethod())
              .addMethod(getSearchMemoriesMethod())
              .addMethod(getListNamespacesMethod())
              .addMethod(getDeleteMemoryMethod())
              .addMethod(getGetMemoryUsageMethod())
              .addMethod(getListTopMemoryUsageMethod())
              .addMethod(getGetMemoryIndexStatusMethod())
              .addMethod(getPutMemoryMethod())
              .addMethod(getUpdateMemoryMethod())
              .build();
        }
      }
    }
    return result;
  }
}
