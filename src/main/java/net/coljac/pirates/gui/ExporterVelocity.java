package net.coljac.pirates.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.LogChute;

/**
 * The Class ExporterVelocity.
 */
public class ExporterVelocity {

    /**
     * The Class VelocityLogger.
     */
    private static final class VelocityLogger implements LogChute {
        /**
         * Initialisation.
         * 
         * @param runtimeServices
         *            RuntimeServices.
         * @throws Exception
         *             Erreur.
         */
        @Override
        public void init(final RuntimeServices runtimeServices) throws Exception {

        }

        /**
         * 
         * Loggin autorisé.
         * 
         * @param level
         *            Niveau.
         * @return true/false.
         */
        @Override
        public boolean isLevelEnabled(final int level) {
            return true;
        }

        /**
         * Logging.
         * 
         * @param level
         *            Level.
         * @param message
         *            Message.
         */
        @Override
        public void log(final int level, final String message) {
            //
        }

        /**
         * Logging.
         * 
         * @param level
         *            Level.
         * @param message
         *            Message.
         * @param throwable
         *            Throwable.
         */
        @Override
        public void log(final int level, final String message, final Throwable throwable) {
            //
        }
    }

    /** The velocity context. */
    private VelocityContext velocityContext;

    /** The template. */
    private String template;

    /**
     * Instantiates a new exporter velocity.
     */
    public ExporterVelocity() {
        Velocity.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM, new VelocityLogger());
        try {
            Velocity.init();
        } catch (final Exception e) {
        }
        velocityContext = new VelocityContext();
    }

    /**
     * Instantiates a new exporter velocity.
     * 
     * @param template
     *            the template
     */
    public ExporterVelocity(final String template) {
        this();
        this.template = template;
    }

    /**
     * Adds the context.
     * 
     * @param key
     *            the key
     * @param object
     *            the object
     */
    public void addContext(final String key, final Object object) {
        velocityContext.put(key, object);
    }

    /**
     * Clear context.
     */
    public void clearContext() {
        velocityContext = new VelocityContext();

    }

    /**
     * Gets the input stream reader.
     * 
     * @param templateName
     *            the template name
     * @return the input stream reader
     */
    private Reader getInputStreamReader(final String templateName) {
        final File templateFile = new File("./" + templateName);
        if (templateFile.exists()) {
            try {
                return new FileReader(templateFile);
            } catch (final FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            final InputStream is = this.getClass().getResourceAsStream("/data/" + templateName);
            if (is == null) {
                throw new RuntimeException("Can't find [/data/" + templateName + "]");
            }
            return new InputStreamReader(is);
        }
    }

    /**
     * Gets the template.
     * 
     * @return the template
     */
    public String getTemplate() {
        return template;
    }

    /**
     * Gets the velocity context.
     * 
     * @return the velocity context
     */
    public VelocityContext getVelocityContext() {
        return velocityContext;
    }

    /**
     * Render.
     * 
     * @return the string
     */
    public String render() {
        try {
            final StringWriter w = new StringWriter();
            Velocity.evaluate(velocityContext, w, null, getInputStreamReader(template));
            return w.toString();
        } catch (final ParseErrorException e) {
            throw new RuntimeException(e);
        } catch (final MethodInvocationException e) {
            throw new RuntimeException(e);
        } catch (final ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the template.
     * 
     * @param template
     *            the new template
     */
    public void setTemplate(final String template) {
        this.template = template;
    }

}
