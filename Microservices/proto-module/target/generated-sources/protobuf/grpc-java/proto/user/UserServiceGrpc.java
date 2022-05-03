package proto.user;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.22.1)",
    comments = "Source: user/user.proto")
public final class UserServiceGrpc {

  private UserServiceGrpc() {}

  public static final String SERVICE_NAME = "user.UserService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<proto.user.Input,
      proto.user.Output> getAddUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "addUser",
      requestType = proto.user.Input.class,
      responseType = proto.user.Output.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.user.Input,
      proto.user.Output> getAddUserMethod() {
    io.grpc.MethodDescriptor<proto.user.Input, proto.user.Output> getAddUserMethod;
    if ((getAddUserMethod = UserServiceGrpc.getAddUserMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getAddUserMethod = UserServiceGrpc.getAddUserMethod) == null) {
          UserServiceGrpc.getAddUserMethod = getAddUserMethod = 
              io.grpc.MethodDescriptor.<proto.user.Input, proto.user.Output>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "user.UserService", "addUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.user.Input.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.user.Output.getDefaultInstance()))
                  .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("addUser"))
                  .build();
          }
        }
     }
     return getAddUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.user.Input1,
      proto.user.Output> getLogInUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "logInUser",
      requestType = proto.user.Input1.class,
      responseType = proto.user.Output.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.user.Input1,
      proto.user.Output> getLogInUserMethod() {
    io.grpc.MethodDescriptor<proto.user.Input1, proto.user.Output> getLogInUserMethod;
    if ((getLogInUserMethod = UserServiceGrpc.getLogInUserMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getLogInUserMethod = UserServiceGrpc.getLogInUserMethod) == null) {
          UserServiceGrpc.getLogInUserMethod = getLogInUserMethod = 
              io.grpc.MethodDescriptor.<proto.user.Input1, proto.user.Output>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "user.UserService", "logInUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.user.Input1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.user.Output.getDefaultInstance()))
                  .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("logInUser"))
                  .build();
          }
        }
     }
     return getLogInUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.user.Input2,
      proto.user.Output> getInvalidateUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "invalidateUser",
      requestType = proto.user.Input2.class,
      responseType = proto.user.Output.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.user.Input2,
      proto.user.Output> getInvalidateUserMethod() {
    io.grpc.MethodDescriptor<proto.user.Input2, proto.user.Output> getInvalidateUserMethod;
    if ((getInvalidateUserMethod = UserServiceGrpc.getInvalidateUserMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getInvalidateUserMethod = UserServiceGrpc.getInvalidateUserMethod) == null) {
          UserServiceGrpc.getInvalidateUserMethod = getInvalidateUserMethod = 
              io.grpc.MethodDescriptor.<proto.user.Input2, proto.user.Output>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "user.UserService", "invalidateUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.user.Input2.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.user.Output.getDefaultInstance()))
                  .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("invalidateUser"))
                  .build();
          }
        }
     }
     return getInvalidateUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.user.updateUserInfoInput,
      proto.user.OutputMessage> getUpdateUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "updateUser",
      requestType = proto.user.updateUserInfoInput.class,
      responseType = proto.user.OutputMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.user.updateUserInfoInput,
      proto.user.OutputMessage> getUpdateUserMethod() {
    io.grpc.MethodDescriptor<proto.user.updateUserInfoInput, proto.user.OutputMessage> getUpdateUserMethod;
    if ((getUpdateUserMethod = UserServiceGrpc.getUpdateUserMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getUpdateUserMethod = UserServiceGrpc.getUpdateUserMethod) == null) {
          UserServiceGrpc.getUpdateUserMethod = getUpdateUserMethod = 
              io.grpc.MethodDescriptor.<proto.user.updateUserInfoInput, proto.user.OutputMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "user.UserService", "updateUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.user.updateUserInfoInput.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.user.OutputMessage.getDefaultInstance()))
                  .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("updateUser"))
                  .build();
          }
        }
     }
     return getUpdateUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.user.InputForGetUserByEmail,
      proto.user.Output> getGetUserByEmailMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getUserByEmail",
      requestType = proto.user.InputForGetUserByEmail.class,
      responseType = proto.user.Output.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.user.InputForGetUserByEmail,
      proto.user.Output> getGetUserByEmailMethod() {
    io.grpc.MethodDescriptor<proto.user.InputForGetUserByEmail, proto.user.Output> getGetUserByEmailMethod;
    if ((getGetUserByEmailMethod = UserServiceGrpc.getGetUserByEmailMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getGetUserByEmailMethod = UserServiceGrpc.getGetUserByEmailMethod) == null) {
          UserServiceGrpc.getGetUserByEmailMethod = getGetUserByEmailMethod = 
              io.grpc.MethodDescriptor.<proto.user.InputForGetUserByEmail, proto.user.Output>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "user.UserService", "getUserByEmail"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.user.InputForGetUserByEmail.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.user.Output.getDefaultInstance()))
                  .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("getUserByEmail"))
                  .build();
          }
        }
     }
     return getGetUserByEmailMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.user.InputUpdateEducation,
      proto.user.OutputMessage> getUpdateEducationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "updateEducation",
      requestType = proto.user.InputUpdateEducation.class,
      responseType = proto.user.OutputMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.user.InputUpdateEducation,
      proto.user.OutputMessage> getUpdateEducationMethod() {
    io.grpc.MethodDescriptor<proto.user.InputUpdateEducation, proto.user.OutputMessage> getUpdateEducationMethod;
    if ((getUpdateEducationMethod = UserServiceGrpc.getUpdateEducationMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getUpdateEducationMethod = UserServiceGrpc.getUpdateEducationMethod) == null) {
          UserServiceGrpc.getUpdateEducationMethod = getUpdateEducationMethod = 
              io.grpc.MethodDescriptor.<proto.user.InputUpdateEducation, proto.user.OutputMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "user.UserService", "updateEducation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.user.InputUpdateEducation.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.user.OutputMessage.getDefaultInstance()))
                  .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("updateEducation"))
                  .build();
          }
        }
     }
     return getUpdateEducationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.user.InputUpdateWorkExperience,
      proto.user.OutputMessage> getUpdateWorkExperienceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "updateWorkExperience",
      requestType = proto.user.InputUpdateWorkExperience.class,
      responseType = proto.user.OutputMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.user.InputUpdateWorkExperience,
      proto.user.OutputMessage> getUpdateWorkExperienceMethod() {
    io.grpc.MethodDescriptor<proto.user.InputUpdateWorkExperience, proto.user.OutputMessage> getUpdateWorkExperienceMethod;
    if ((getUpdateWorkExperienceMethod = UserServiceGrpc.getUpdateWorkExperienceMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getUpdateWorkExperienceMethod = UserServiceGrpc.getUpdateWorkExperienceMethod) == null) {
          UserServiceGrpc.getUpdateWorkExperienceMethod = getUpdateWorkExperienceMethod = 
              io.grpc.MethodDescriptor.<proto.user.InputUpdateWorkExperience, proto.user.OutputMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "user.UserService", "updateWorkExperience"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.user.InputUpdateWorkExperience.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.user.OutputMessage.getDefaultInstance()))
                  .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("updateWorkExperience"))
                  .build();
          }
        }
     }
     return getUpdateWorkExperienceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.user.InputForGetUserByEmail,
      proto.user.OutputEducations> getGetEducationsUserByEmailMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getEducationsUserByEmail",
      requestType = proto.user.InputForGetUserByEmail.class,
      responseType = proto.user.OutputEducations.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.user.InputForGetUserByEmail,
      proto.user.OutputEducations> getGetEducationsUserByEmailMethod() {
    io.grpc.MethodDescriptor<proto.user.InputForGetUserByEmail, proto.user.OutputEducations> getGetEducationsUserByEmailMethod;
    if ((getGetEducationsUserByEmailMethod = UserServiceGrpc.getGetEducationsUserByEmailMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getGetEducationsUserByEmailMethod = UserServiceGrpc.getGetEducationsUserByEmailMethod) == null) {
          UserServiceGrpc.getGetEducationsUserByEmailMethod = getGetEducationsUserByEmailMethod = 
              io.grpc.MethodDescriptor.<proto.user.InputForGetUserByEmail, proto.user.OutputEducations>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "user.UserService", "getEducationsUserByEmail"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.user.InputForGetUserByEmail.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.user.OutputEducations.getDefaultInstance()))
                  .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("getEducationsUserByEmail"))
                  .build();
          }
        }
     }
     return getGetEducationsUserByEmailMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UserServiceStub newStub(io.grpc.Channel channel) {
    return new UserServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UserServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new UserServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UserServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new UserServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class UserServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void addUser(proto.user.Input request,
        io.grpc.stub.StreamObserver<proto.user.Output> responseObserver) {
      asyncUnimplementedUnaryCall(getAddUserMethod(), responseObserver);
    }

    /**
     */
    public void logInUser(proto.user.Input1 request,
        io.grpc.stub.StreamObserver<proto.user.Output> responseObserver) {
      asyncUnimplementedUnaryCall(getLogInUserMethod(), responseObserver);
    }

    /**
     */
    public void invalidateUser(proto.user.Input2 request,
        io.grpc.stub.StreamObserver<proto.user.Output> responseObserver) {
      asyncUnimplementedUnaryCall(getInvalidateUserMethod(), responseObserver);
    }

    /**
     */
    public void updateUser(proto.user.updateUserInfoInput request,
        io.grpc.stub.StreamObserver<proto.user.OutputMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateUserMethod(), responseObserver);
    }

    /**
     */
    public void getUserByEmail(proto.user.InputForGetUserByEmail request,
        io.grpc.stub.StreamObserver<proto.user.Output> responseObserver) {
      asyncUnimplementedUnaryCall(getGetUserByEmailMethod(), responseObserver);
    }

    /**
     */
    public void updateEducation(proto.user.InputUpdateEducation request,
        io.grpc.stub.StreamObserver<proto.user.OutputMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateEducationMethod(), responseObserver);
    }

    /**
     */
    public void updateWorkExperience(proto.user.InputUpdateWorkExperience request,
        io.grpc.stub.StreamObserver<proto.user.OutputMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateWorkExperienceMethod(), responseObserver);
    }

    /**
     */
    public void getEducationsUserByEmail(proto.user.InputForGetUserByEmail request,
        io.grpc.stub.StreamObserver<proto.user.OutputEducations> responseObserver) {
      asyncUnimplementedUnaryCall(getGetEducationsUserByEmailMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAddUserMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.user.Input,
                proto.user.Output>(
                  this, METHODID_ADD_USER)))
          .addMethod(
            getLogInUserMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.user.Input1,
                proto.user.Output>(
                  this, METHODID_LOG_IN_USER)))
          .addMethod(
            getInvalidateUserMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.user.Input2,
                proto.user.Output>(
                  this, METHODID_INVALIDATE_USER)))
          .addMethod(
            getUpdateUserMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.user.updateUserInfoInput,
                proto.user.OutputMessage>(
                  this, METHODID_UPDATE_USER)))
          .addMethod(
            getGetUserByEmailMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.user.InputForGetUserByEmail,
                proto.user.Output>(
                  this, METHODID_GET_USER_BY_EMAIL)))
          .addMethod(
            getUpdateEducationMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.user.InputUpdateEducation,
                proto.user.OutputMessage>(
                  this, METHODID_UPDATE_EDUCATION)))
          .addMethod(
            getUpdateWorkExperienceMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.user.InputUpdateWorkExperience,
                proto.user.OutputMessage>(
                  this, METHODID_UPDATE_WORK_EXPERIENCE)))
          .addMethod(
            getGetEducationsUserByEmailMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.user.InputForGetUserByEmail,
                proto.user.OutputEducations>(
                  this, METHODID_GET_EDUCATIONS_USER_BY_EMAIL)))
          .build();
    }
  }

  /**
   */
  public static final class UserServiceStub extends io.grpc.stub.AbstractStub<UserServiceStub> {
    private UserServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private UserServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new UserServiceStub(channel, callOptions);
    }

    /**
     */
    public void addUser(proto.user.Input request,
        io.grpc.stub.StreamObserver<proto.user.Output> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAddUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void logInUser(proto.user.Input1 request,
        io.grpc.stub.StreamObserver<proto.user.Output> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getLogInUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void invalidateUser(proto.user.Input2 request,
        io.grpc.stub.StreamObserver<proto.user.Output> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getInvalidateUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateUser(proto.user.updateUserInfoInput request,
        io.grpc.stub.StreamObserver<proto.user.OutputMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getUserByEmail(proto.user.InputForGetUserByEmail request,
        io.grpc.stub.StreamObserver<proto.user.Output> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetUserByEmailMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateEducation(proto.user.InputUpdateEducation request,
        io.grpc.stub.StreamObserver<proto.user.OutputMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateEducationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateWorkExperience(proto.user.InputUpdateWorkExperience request,
        io.grpc.stub.StreamObserver<proto.user.OutputMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateWorkExperienceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getEducationsUserByEmail(proto.user.InputForGetUserByEmail request,
        io.grpc.stub.StreamObserver<proto.user.OutputEducations> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetEducationsUserByEmailMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class UserServiceBlockingStub extends io.grpc.stub.AbstractStub<UserServiceBlockingStub> {
    private UserServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private UserServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new UserServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public proto.user.Output addUser(proto.user.Input request) {
      return blockingUnaryCall(
          getChannel(), getAddUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.user.Output logInUser(proto.user.Input1 request) {
      return blockingUnaryCall(
          getChannel(), getLogInUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.user.Output invalidateUser(proto.user.Input2 request) {
      return blockingUnaryCall(
          getChannel(), getInvalidateUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.user.OutputMessage updateUser(proto.user.updateUserInfoInput request) {
      return blockingUnaryCall(
          getChannel(), getUpdateUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.user.Output getUserByEmail(proto.user.InputForGetUserByEmail request) {
      return blockingUnaryCall(
          getChannel(), getGetUserByEmailMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.user.OutputMessage updateEducation(proto.user.InputUpdateEducation request) {
      return blockingUnaryCall(
          getChannel(), getUpdateEducationMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.user.OutputMessage updateWorkExperience(proto.user.InputUpdateWorkExperience request) {
      return blockingUnaryCall(
          getChannel(), getUpdateWorkExperienceMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.user.OutputEducations getEducationsUserByEmail(proto.user.InputForGetUserByEmail request) {
      return blockingUnaryCall(
          getChannel(), getGetEducationsUserByEmailMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class UserServiceFutureStub extends io.grpc.stub.AbstractStub<UserServiceFutureStub> {
    private UserServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private UserServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new UserServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.user.Output> addUser(
        proto.user.Input request) {
      return futureUnaryCall(
          getChannel().newCall(getAddUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.user.Output> logInUser(
        proto.user.Input1 request) {
      return futureUnaryCall(
          getChannel().newCall(getLogInUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.user.Output> invalidateUser(
        proto.user.Input2 request) {
      return futureUnaryCall(
          getChannel().newCall(getInvalidateUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.user.OutputMessage> updateUser(
        proto.user.updateUserInfoInput request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.user.Output> getUserByEmail(
        proto.user.InputForGetUserByEmail request) {
      return futureUnaryCall(
          getChannel().newCall(getGetUserByEmailMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.user.OutputMessage> updateEducation(
        proto.user.InputUpdateEducation request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateEducationMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.user.OutputMessage> updateWorkExperience(
        proto.user.InputUpdateWorkExperience request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateWorkExperienceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.user.OutputEducations> getEducationsUserByEmail(
        proto.user.InputForGetUserByEmail request) {
      return futureUnaryCall(
          getChannel().newCall(getGetEducationsUserByEmailMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD_USER = 0;
  private static final int METHODID_LOG_IN_USER = 1;
  private static final int METHODID_INVALIDATE_USER = 2;
  private static final int METHODID_UPDATE_USER = 3;
  private static final int METHODID_GET_USER_BY_EMAIL = 4;
  private static final int METHODID_UPDATE_EDUCATION = 5;
  private static final int METHODID_UPDATE_WORK_EXPERIENCE = 6;
  private static final int METHODID_GET_EDUCATIONS_USER_BY_EMAIL = 7;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final UserServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(UserServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD_USER:
          serviceImpl.addUser((proto.user.Input) request,
              (io.grpc.stub.StreamObserver<proto.user.Output>) responseObserver);
          break;
        case METHODID_LOG_IN_USER:
          serviceImpl.logInUser((proto.user.Input1) request,
              (io.grpc.stub.StreamObserver<proto.user.Output>) responseObserver);
          break;
        case METHODID_INVALIDATE_USER:
          serviceImpl.invalidateUser((proto.user.Input2) request,
              (io.grpc.stub.StreamObserver<proto.user.Output>) responseObserver);
          break;
        case METHODID_UPDATE_USER:
          serviceImpl.updateUser((proto.user.updateUserInfoInput) request,
              (io.grpc.stub.StreamObserver<proto.user.OutputMessage>) responseObserver);
          break;
        case METHODID_GET_USER_BY_EMAIL:
          serviceImpl.getUserByEmail((proto.user.InputForGetUserByEmail) request,
              (io.grpc.stub.StreamObserver<proto.user.Output>) responseObserver);
          break;
        case METHODID_UPDATE_EDUCATION:
          serviceImpl.updateEducation((proto.user.InputUpdateEducation) request,
              (io.grpc.stub.StreamObserver<proto.user.OutputMessage>) responseObserver);
          break;
        case METHODID_UPDATE_WORK_EXPERIENCE:
          serviceImpl.updateWorkExperience((proto.user.InputUpdateWorkExperience) request,
              (io.grpc.stub.StreamObserver<proto.user.OutputMessage>) responseObserver);
          break;
        case METHODID_GET_EDUCATIONS_USER_BY_EMAIL:
          serviceImpl.getEducationsUserByEmail((proto.user.InputForGetUserByEmail) request,
              (io.grpc.stub.StreamObserver<proto.user.OutputEducations>) responseObserver);
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

  private static abstract class UserServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UserServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return proto.user.User.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("UserService");
    }
  }

  private static final class UserServiceFileDescriptorSupplier
      extends UserServiceBaseDescriptorSupplier {
    UserServiceFileDescriptorSupplier() {}
  }

  private static final class UserServiceMethodDescriptorSupplier
      extends UserServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    UserServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (UserServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UserServiceFileDescriptorSupplier())
              .addMethod(getAddUserMethod())
              .addMethod(getLogInUserMethod())
              .addMethod(getInvalidateUserMethod())
              .addMethod(getUpdateUserMethod())
              .addMethod(getGetUserByEmailMethod())
              .addMethod(getUpdateEducationMethod())
              .addMethod(getUpdateWorkExperienceMethod())
              .addMethod(getGetEducationsUserByEmailMethod())
              .build();
        }
      }
    }
    return result;
  }
}
