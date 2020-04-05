package com.pdoming.kernel.core.functional;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A Result<T> represents the result a computation that either yield a
 * value or failed.
 *
 * Result<T> has static constructors such as {@link Result#ok(Object)}
 * or {@link Result#error(String)} that ease the creation of new results
 * in a similar way to exceptions.
 *
 * The API of Result<T> is similar to the one of {@link java.util.Optional}
 * as it allows to retrieve the inner value, provided a fallback one or
 * raise an exception in case the result is an error.
 *
 * @param <T> the type of the result
 */
public final class Result<T> {

	/**
	 * The inner value of the result. This value stores the result
	 * of a computation that finished correctly
	 */
    private final T value;

	/**
	 * The error that happened during a computation
	 */
	private final Error error;

	private static final Result<?> EMPTY_OK_RESULT = new Result<>(new Object(), null);

	/**
	 * Private constructor of {@link Result}
	 * @param value the result of a computation
	 * @param error the error produced by a computation
	 */
    private Result(T value, Error error) {
        if ( value == null && error == null )
            throw new IllegalArgumentException("Both value and error can't be null");
        this.value = value;
        this.error = error;
    }

    /* ------------------------ Static constructors ------------------------ */

	/**
	 * Create a new {@link Result} whose value is not null
	 * @param value the ok result
	 * @param <K> the type of value
	 * @return a new ok {@link Result}
	 */
    public static <K> Result<K> ok(K value) {
        return new Result<>(value, null);
    }

	/**
	 * Create a new {@link Result} whose value is not null
	 * @return a new ok {@link Result}
	 */
	public static Result<?> ok() {
		return Result.EMPTY_OK_RESULT;
	}

	/**
	 * Create a new {@link Result} that describes an error based
	 * on a message
	 * @param message the error message
	 * @param <K> the type of the value
	 * @return a new error {@link Result}
	 */
	public static <K> Result<K> error(String message) {
        return new Result<>(null, new SimpleError( message ));
    }

	/**
	 * Create a new {@link Result} that describes an error based
	 * on an exception
	 * @param throwable the exception that caused the error
	 * @param <K> the type of the value
	 * @return a new error {@link Result}
	 */
    public static <K> Result<K> error(Throwable throwable) {
        return new Result<>(null, new ExceptionError<>( throwable ));
    }

	/**
	 * Create a new {@link Result} that describes an error based
	 * on a error message and an exception
	 * @param message the error message
	 * @param throwable the exception that caused the error
	 * @param <K> the type of the value
	 * @return a new error {@link Result}
	 */
    public static <K> Result<K> error(String message, Throwable throwable) {
	    Objects.requireNonNull(message);
	    Objects.requireNonNull(throwable);
	    return new Result<>(null, new ExceptionError<>( message, throwable));
    }

	/**
	 * Create a new {@link Result} that describes an error based
	 * on a error message and another {@link Error}
	 * @param message the error message
	 * @param error the error that caused this error
	 * @param <K> the type of the value
	 * @return a new error {@link Result}
	 */
    public static <K> Result<K> error(String message, Error error) {
	    Objects.requireNonNull(message);
	    Objects.requireNonNull(error);
        return new Result<>(null, new NestedError( message, error ));
    }

    /* ------------------------ Main methods ------------------------ */

	/**
	 * Retrieves the value of this result
	 * @return the inner value
	 * @throws NoSuchElementException if there is no value
	 */
    public T get() throws NoSuchElementException {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

	/**
	 * Test if this result was OK (there is a result instead of an error)
	 * @return true if the result is ok
	 */
	public boolean isOk() {
        return value != null;
    }

	/**
	 * Test if this result was ERROR (there is an error instead of a result)
	 * @return true if the result is error
	 */
	public boolean isError() {
        return error != null;
    }

	/**
	 * Applies the given {@link Function} to the result if it's
	 * present and wrap it around another {@link Result} or do nothing
	 * @param mapper the function to apply to the result
	 * @return a new {@link Result}, either ok or error
	 */
	public Result<T> map(Function<? super T, ? extends T> mapper) {
        Objects.requireNonNull(mapper);
        if (isOk())
            return Result.ok( mapper.apply(value) );
        else {
            return this;
        }
    }

    public Result<T> flatMap(Function<? super T, Result<T>> mapper) {
        Objects.requireNonNull(mapper);
        if (isOk())
            return mapper.apply(value);
        else {
            return this;
        }
    }

	/**
	 * Applies the given {@link Consumer} to the result if it's present
	 * @param consumer the function to apply to the result
	 */
	public void ifOk(Consumer<? super T> consumer) {
		Objects.requireNonNull(consumer);
		if (isOk()) {
			consumer.accept( value );
		}
	}

	/**
	 * Applies the given {@link Consumer} to the error if it's present
	 * @param consumer the function to apply to the error
	 */
	public void ifError(Consumer<? super Error> consumer) {
		Objects.requireNonNull(consumer);
		if (isError()) {
			consumer.accept( error );
		}
	}

	public void apply(Consumer<? super T> valueConsumer, Consumer<? super Error> errorConsumer) {
		this.ifOk(valueConsumer);
		this.ifError(errorConsumer);
	}

	/**
	 * Returns the value of the result if it's present or the
	 * fallback value if not
	 * @param other the fallback value to return
	 * @return the value of {@link Result} or the fallback
	 */
	public T orElse(T other) {
        Objects.requireNonNull(other);
        return isOk() ? value : other;
    }

	/**
	 * Returns the value of the result if it's present or the
	 * value supplied by the argument if not
	 * @param other the fallback value supplied
	 * @return the value of {@link Result} or the fallback value supplied
	 */
    public T orElseGet(Supplier<? extends T> other) {
        Objects.requireNonNull(other);
        return isOk() ? value : other.get();
    }

	/**
	 * Returns the value of the result if present or throw the {@link Throwable}
	 * exception produced from applying {@code exceptionSupplier} to the {@link #error}
	 * @param exceptionSupplier the supplier of exceptions
	 * @param <X> the type of the {@link Throwable} to throw
	 * @return the value if it's present
	 * @throws X if an error is present
	 */
	public <X extends Throwable> T orElseThrow(Function<Error, ? extends X> exceptionSupplier) throws X {
        Objects.requireNonNull(exceptionSupplier);
        if (isOk()) {
            return value;
        } else {
            throw exceptionSupplier.apply(error);
        }
    }

	/**
	 * Captures the result of calling {@link Callable#call()} on the given
	 * argument into a {@link Result}
	 * @param callable the computation to perform
	 * @param <T> the type returned by the {@link Callable}
	 * @return a new {@link Result}, either ok or error
	 */
	public static <T> Result<T> capture(Callable<T> callable) {
	    Objects.requireNonNull(callable);
        try {
            return Result.ok(callable.call());
        } catch (Exception ex) {
            return Result.error(ex);
        }
    }

    /**
     * If the value is present in this {@link Result}, then return it
     * or else throw an {@link InvalidResultException} with the error
     * @return the value held by this {@link Result}
     * @throws InvalidResultException if there is an error instead of a value
     */
    public T orElsePropagate() throws InvalidResultException {
        if (isOk()) {
            return value;
        } else {
            throw InvalidResultException.from(error);
        }
    }

    // ----------------------------------------------------------------------------------------

    /**
     * Exception that describes the existence of an error in a {@link Result}
     */
    private static class InvalidResultException extends RuntimeException {

	    /**
	     * Creates a new instance of {@link InvalidResultException} with the
	     * provided {@code message} and {@code cause}
	     * @param message the error message
	     * @param cause the cause that produced this exception
	     */
        public InvalidResultException(String message, Throwable cause) {
            super(message, cause);
        }

	    /**
	     * Creates a new instance of {@link InvalidResultException} with the
	     * provided {@code message}
	     * @param message the error message
	     */
        public InvalidResultException(String message) {
            super(message);
        }

	    /**
	     * Creates a new instance of {@link InvalidResultException} with the
	     * provided {@code cause}
	     * @param cause the cause that produced this exception
	     */
        public InvalidResultException(Throwable cause) {
            super(cause);
        }

        /**
         * Transforms an {@link Error} into a suitable {@link InvalidResultException}
         * @param error a non null error
         * @return the error transformed into an exception
         */
        public static InvalidResultException from(Error error) {
            Objects.requireNonNull(error);

            if ( error instanceof ExceptionError)
                return new InvalidResultException( ((ExceptionError) error).throwable );
            if ( error instanceof SimpleError )
                return new InvalidResultException( ((SimpleError) error).message );
            if ( error instanceof NestedError )
                return new InvalidResultException( ((NestedError) error).message, from( ((NestedError) error).error ) );

            // Delegate to some other implementation of Error
            return new InvalidResultException( error.getCause() );
        }
    }

    // -------------------------------- Errors ------------------------------------

    /**
     * Simple interface to represent an error that may happen during a computation
     */
    public interface Error {

        /**
         * Return the reason for this {@link Error}
         * @return the reason of the error
         */
        String getCause();

        /**
         * Return this error transformed as a suitable {@link InvalidResultException}
         * @return the error in the form of an {@link InvalidResultException}
         */
        default InvalidResultException asException() {
            return InvalidResultException.from( this );
        }
    }

	/**
	 * Implementor of {@link Error} composed by a simple error message
	 */
	private static class SimpleError implements Error {
		/**
		 * Specifies relevant information about the error
		 * eg reason, context, line, cause, etc
		 */
		private final String message;

		/**
		 * Creates a new {@link SimpleError}
		 * @param message a non null message
		 */
		private SimpleError(String message) {
			this.message = message;
		}

		@Override
		public String getCause() {
			return message;
		}
	}

    /**
     * Implementor of {@link Error} formed by an {@link Throwable}
     */
    private static class ExceptionError<X extends Throwable> implements Error {

	    /**
	     * Specifies relevant information about the error
	     * eg reason, context, line, cause, etc
	     */
		private final String message;
        /**
         * The {@link Throwable} that caused the {@link Error} to produce
         */
        private final X throwable;

        /**
         * Creates a new {@link ExceptionError}
         * @param message a non null error message
         * @param throwable a non null throwable
         */
        private ExceptionError(String message, X throwable) {
	        this.throwable = throwable;
	        this.message = message;
        }

	    /**
	     * Creates a new {@link ExceptionError}
	     * @param throwable a non null throwable
	     */
	    private ExceptionError(X throwable) {
		    this("", throwable);
	    }

        @Override
        public String getCause() {
            Throwable root, cause = root = throwable;
            while ((cause = cause.getCause()) != null) {
                root = cause;
            }
            return message.isEmpty() ? root.getMessage() : message + " - " + root.getMessage();
        }
    }

    /**
     * Implementor of {@link Error} composed of another {@link Error} and a message
     */
    private static class NestedError implements Error {
        /**
         * Specifies relevant information about the error
         * eg reason, context, line, cause, etc
         */
        private final String message;

        /**
         *  The {@link Error} that caused this {@link NestedError} to produce
         */
        private final Error error;

        /**
         * Creates a new {@link NestedError}
         * @param message a non null message
         * @param error a non null error
         */
        private NestedError(String message, Error error) {
            this.message = message;
            this.error = error;
        }

        @Override
        public String getCause() {
            return message + " - " + error.getCause();
        }
    }
}
