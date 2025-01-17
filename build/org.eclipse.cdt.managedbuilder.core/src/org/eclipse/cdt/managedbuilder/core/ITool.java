/*******************************************************************************
 *  Copyright (c) 2003, 2010, 2023 IBM Corporation and others.
 *
 *  This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *     IBM - Initial API and implementation
 *     Serge Beauchamp (Freescale Semiconductor)
 *******************************************************************************/
package org.eclipse.cdt.managedbuilder.core;

import java.util.List;

import org.eclipse.cdt.core.settings.model.extension.CLanguageData;
import org.eclipse.cdt.managedbuilder.makegen.IManagedDependencyGenerator;
import org.eclipse.cdt.managedbuilder.makegen.IManagedDependencyGeneratorType;
import org.eclipse.core.runtime.IPath;

/**
 * This interface represents a utility of some sort that is used in the build process.
 * A tool will generally process one or more resources to produce output resources.
 * Most tools have a set of options that can be used to modify the behavior of the tool.
 *
 * @noextend This class is not intended to be subclassed by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ITool extends IHoldsOptions {
	// Schema element names
	public static final String COMMAND = "command"; //$NON-NLS-1$
	public static final String COMMAND_LINE_PATTERN = "commandLinePattern"; //$NON-NLS-1$
	public static final String COMMAND_LINE_GENERATOR = "commandLineGenerator"; //$NON-NLS-1$
	public static final String DEP_CALC_ID = "dependencyCalculator"; //$NON-NLS-1$
	public static final String INTERFACE_EXTS = "headerExtensions"; //$NON-NLS-1$
	public static final String NATURE = "natureFilter"; //$NON-NLS-1$
	public static final String OUTPUT_FLAG = "outputFlag"; //$NON-NLS-1$
	public static final String INPUT_TYPE = "inputType"; //$NON-NLS-1$
	public static final String OUTPUT_TYPE = "outputType"; //$NON-NLS-1$
	public static final String OUTPUT_PREFIX = "outputPrefix"; //$NON-NLS-1$
	public static final String OUTPUTS = "outputs"; //$NON-NLS-1$
	public static final String SOURCES = "sources"; //$NON-NLS-1$
	public static final String ADVANCED_INPUT_CATEGORY = "advancedInputCategory"; //$NON-NLS-1$
	public static final String CUSTOM_BUILD_STEP = "customBuildStep"; //$NON-NLS-1$
	public static final String ANNOUNCEMENT = "announcement"; //$NON-NLS-1$
	public static final String TOOL_ELEMENT_NAME = "tool"; //$NON-NLS-1$
	public static final String WHITE_SPACE = " "; //$NON-NLS-1$
	public static final String EMPTY_STRING = ""; //$NON-NLS-1$
	public static final String IS_SYSTEM = "isSystem"; //$NON-NLS-1$
	/**
	 * @since 9.6
	 */
	public static final String ARGUMENT_FILE_FORMAT = "argumentFileFormat"; //$NON-NLS-1$
	/**
	 * @since 8.4
	 */
	public static final String IS_HIDDEN = "isHidden"; //$NON-NLS-1$

	public static final String VERSIONS_SUPPORTED = "versionsSupported"; //$NON-NLS-1$
	public static final String CONVERT_TO_ID = "convertToId"; //$NON-NLS-1$
	public static final String OPTIONPATHCONVERTER = "optionPathConverter"; //$NON-NLS-1$

	public static final String SUPPORTS_MANAGED_BUILD = "supportsManagedBuild"; //$NON-NLS-1$

	public static final int FILTER_C = 0;
	public static final int FILTER_CC = 1;
	public static final int FILTER_BOTH = 2;

	/**
	 * Returns the tool-chain or resource configuration that is the parent of this tool.
	 *
	 * @return IBuildObject
	 */
	public IBuildObject getParent();

	/**
	 * Creates a child InputType for this tool.
	 *
	 * @param superClass The superClass, if any
	 * @param Id The id for the new InputType
	 * @param name The name for the new InputType
	 * @param isExtensionElement Indicates whether this is an extension element or a managed project element
	 *
	 * @return IInputType
	 * @since 3.0
	 */
	public IInputType createInputType(IInputType superClass, String Id, String name, boolean isExtensionElement);

	/**
	 * Removes an InputType from the tool's list.
	 *
	 * @since 3.0
	 */
	public void removeInputType(IInputType type);

	/**
	 * Returns the complete list of input types that are available for this tool.
	 * The list is a merging of the input types specified for this tool with the
	 * input types of its superclasses.  The lowest input type instance in the hierarchy
	 * takes precedence.
	 *
	 * @return IInputType[]
	 * @since 3.0
	 */
	public IInputType[] getInputTypes();

	/**
	 * Returns the <code>IInputType</code> in the tool with the specified
	 * ID. This is an efficient search in the receiver.
	 *
	 * <p>If the receiver does not have an InputType with that ID, the method
	 * returns <code>null</code>. It is the responsibility of the caller to
	 * verify the return value.
	 *
	 * @param id unique identifier of the InputType to search for
	 * @return <code>IInputType</code>
	 * @since 3.0
	 */
	public IInputType getInputTypeById(String id);

	/**
	 * Returns the <code>IInputType</code> in the tool that uses the
	 * specified extension.
	 *
	 * <p>If the receiver does not have an InputType that uses the extension,
	 * the method returns <code>null</code>. It is the responsibility of the
	 * caller to verify the return value.
	 *
	 * @param inputExtension File extension
	 * @return <code>IInputType</code>
	 * @since 3.0
	 */
	public IInputType getInputType(String inputExtension);

	/**
	 * Returns the primary <code>IInputType</code> in this tool
	 *
	 * <p>If the receiver has no InputTypes,
	 * the method returns <code>null</code>. It is the responsibility of the
	 * caller to verify the return value.
	 *
	 * @return <code>IInputType</code>
	 * @since 3.0
	 */
	public IInputType getPrimaryInputType();

	/**
	 * Returns all of the additional input resources of all InputType children.
	 * Note: This does not include the primary InputType and does not include
	 * additional dependencies.
	 *
	 * @return IPath[]
	 */
	public IPath[] getAdditionalResources();

	/**
	 * Returns all of the additional dependency resources of all InputType children.
	 * Note: This does not include the primary InputType and does not include
	 * additional inputs.
	 *
	 * @return IPath[]
	 */
	public IPath[] getAdditionalDependencies();

	/**
	 * Creates a child OutputType for this tool.
	 *
	 * @param superClass The superClass, if any
	 * @param Id The id for the new OutputType
	 * @param name The name for the new OutputType
	 * @param isExtensionElement Indicates whether this is an extension element or a managed project element
	 *
	 * @return IOutputType
	 * @since 3.0
	 */
	public IOutputType createOutputType(IOutputType superClass, String Id, String name, boolean isExtensionElement);

	/**
	 * Removes an OutputType from the tool's list.
	 *
	 * @since 3.0
	 */
	public void removeOutputType(IOutputType type);

	/**
	 * Returns the complete list of output types that are available for this tool.
	 * The list is a merging of the output types specified for this tool with the
	 * output types of its superclasses.  The lowest output type instance in the hierarchy
	 * takes precedence.
	 *
	 * @return IOutputType[]
	 * @since 3.0
	 */
	public IOutputType[] getOutputTypes();

	/**
	 * Get the <code>IOutputType</code> in the receiver with the specified
	 * ID. This is an efficient search in the receiver.
	 *
	 * <p>If the receiver does not have an OutputType with that ID, the method
	 * returns <code>null</code>. It is the responsibility of the caller to
	 * verify the return value.
	 *
	 * @param id unique identifier of the OutputType to search for
	 * @return <code>IOutputType</code>
	 * @since 3.0
	 */
	public IOutputType getOutputTypeById(String id);

	/**
	 * Returns the <code>IOutputType</code> in the tool that creates the
	 * specified extension.
	 *
	 * <p>If the receiver does not have an OutputType that creates the extension,
	 * the method returns <code>null</code>. It is the responsibility of the
	 * caller to verify the return value.
	 *
	 * @param outputExtension File extension
	 * @return <code>IOutputType</code>
	 * @since 3.0
	 */
	public IOutputType getOutputType(String outputExtension);

	/**
	 * Returns the primary <code>IOutputType</code> in this tool
	 *
	 * <p>If the receiver has no OutputTypes,
	 * the method returns <code>null</code>. It is the responsibility of the
	 * caller to verify the return value.
	 *
	 * @return <code>IOutputType</code>
	 * @since 3.0
	 */
	public IOutputType getPrimaryOutputType();

	/**
	 * Returns the <code>ITool</code> that is the superclass of this
	 * tool, or <code>null</code> if the attribute was not specified.
	 *
	 * @return ITool
	 */
	public ITool getSuperClass();

	/**
	 * Returns whether this element is abstract.  Returns <code>false</code>
	 * if the attribute was not specified.
	 * @return boolean
	 */
	public boolean isAbstract();

	/**
	 * Sets the isAbstract attribute of the tool-chain.
	 */
	public void setIsAbstract(boolean b);

	/**
	 * Returns a semi-colon delimited list of child Ids of the superclass'
	 * children that should not be automatically inherited by this element.
	 * Returns an empty string if the attribute was not specified.
	 * @return String
	 */
	public String getUnusedChildren();

	/**
	 * Returns the semicolon separated list of unique IDs of the error parsers associated
	 * with the tool.
	 *
	 * @return String
	 */
	public String getErrorParserIds();

	/**
	 * Returns the ordered list of unique IDs of the error parsers associated with the
	 * tool.
	 *
	 * @return String[]
	 */
	public String[] getErrorParserList();

	/**
	 * Sets the semicolon separated list of error parser ids
	 */
	public void setErrorParserIds(String ids);

	/**
	 * Returns the list of valid source extensions this tool knows how to build.
	 * The list may be empty but will never be <code>null</code>.
	 *
	 * @return List
	 * @deprecated - use getPrimaryInputExtensions or getAllInputExtensions
	 */
	@Deprecated
	public List<String> getInputExtensions();

	/**
	 * Returns the array of valid primary source extensions this tool knows how to build.
	 * The array may be empty but will never be <code>null</code>.
	 *
	 * @return String[]
	 */
	public String[] getPrimaryInputExtensions();

	/**
	 * Returns the array of all valid source extensions this tool knows how to build.
	 * The array may be empty but will never be <code>null</code>.
	 *
	 * @return String[]
	 */
	public String[] getAllInputExtensions();

	/**
	 * Returns the default input extension for the primary input of the tool
	 *
	 * @return String
	 */
	public String getDefaultInputExtension();

	/**
	 * Returns the array of all valid dependency extensions for this tool's inputs.
	 * The array may be empty but will never be <code>null</code>.
	 *
	 * @return String[]
	 */
	public String[] getAllDependencyExtensions();

	/**
	 * Returns the list of valid header extensions for this tool.
	 * Returns the value of the headerExtensions attribute
	 * The list may be empty but will never be <code>null</code>.
	 *
	 * @return List
	 * @deprecated - use getDependency* methods
	 */
	@Deprecated
	public List<String> getInterfaceExtensions();

	/**
	 * Answers a constant corresponding to the project nature the tool should be used
	 * for. Possible answers are:
	 *
	 * <dl>
	 * <dt>ITool.FILTER_C
	 * <dd>The tool should only be displayed for C projects. <i>Notes:</i> even
	 * though a C++ project has a C nature, this flag will mask the tool for C++
	 * projects.
	 * <dt>ITool.FILTER_CC
	 * <dd>The tool should only be displayed for C++ projects.
	 * <dt>ITool.FILTER_BOTH
	 * <dd>The tool should be displayed for projects with both natures.
	 * </dl>
	 *
	 * @return int
	 */
	public int getNatureFilter();

	/**
	 * Returns the array of all valid output extensions this tool can create.
	 * The array may be empty but will never be <code>null</code>.
	 *
	 * @return String[]
	 */
	public String[] getAllOutputExtensions();

	/**
	 * Answers all of the output extensions that the receiver can build.
	 * This routine returns the value if the outputs attribute.
	 *
	 * @return <code>String[]</code> of extensions
	 * @deprecated - use getAllOutputExtensions
	 */
	@Deprecated
	public String[] getOutputExtensions();

	/**
	 * Answers all of the output extensions that the receiver can build,
	 * from the value of the outputs attribute
	 *
	 * @return <code>String[]</code> of extensions
	 */
	public String[] getOutputsAttribute();

	/**
	 * Answer the output extension the receiver will create from the input,
	 * or <code>null</code> if the tool does not understand that extension.
	 *
	 * @param inputExtension The extension of the source file.
	 * @return String
	 */
	public String getOutputExtension(String inputExtension);

	/**
	 * Sets all of the output extensions that the receiver can build,
	 * into the outputs attribute.  Note that the outputs attribute is
	 * ignored when one or more outputTypes are specified.
	 */
	public void setOutputsAttribute(String extensions);

	/**
	 * Answers the argument that must be passed to a specific tool in order to
	 * control the name of the output artifact. For example, the GCC compile and
	 * linker use '-o', while the archiver does not.
	 *
	 * @return String
	 */
	public String getOutputFlag();

	/**
	 * Sets the argument that must be passed to a specific tool in order to
	 * control the name of the output artifact. For example, the GCC compile and
	 * linker use '-o', while the archiver does not.
	 */
	public void setOutputFlag(String flag);

	/**
	 * Answers the prefix that the tool should prepend to the name of the build artifact.
	 * For example, a librarian usually prepends 'lib' to the target.a
	 * @return String
	 */
	public String getOutputPrefix();

	/**
	 * Sets the prefix that the tool should prepend to the name of the build artifact.
	 * For example, a librarian usually prepends 'lib' to the target.a
	 *
	 * @see #setOutputPrefixForPrimaryOutput(String)
	 */
	public void setOutputPrefix(String prefix);

	public void setOutputPrefixForPrimaryOutput(String prefix);

	/**
	 * Returns <code>true</code> if the Tool wants the MBS to display the Advanced
	 * Input category that allows the user to specify additional input resources and
	 * dependencies, else <code>false</code>.
	 *
	 * @return boolean
	 */
	public boolean getAdvancedInputCategory();

	/**
	 * Sets whether the Tool wants the MBS to display the Advanced
	 * Input category that allows the user to specify additional input resources and
	 * dependencies.
	 */
	public void setAdvancedInputCategory(boolean display);

	/**
	 * Returns <code>true</code> if the Tool represents a user-define custom build
	 * step, else <code>false</code>.
	 *
	 * @return boolean
	 */
	public boolean getCustomBuildStep();

	/**
	 * Sets whether the Tool represents a user-define custom build step.
	 */
	public void setCustomBuildStep(boolean customBuildStep);

	/**
	 * Returns the announcement string for this tool
	 * @return String
	 */
	public String getAnnouncement();

	/**
	 * Sets the announcement string for this tool
	 */
	public void setAnnouncement(String announcement);

	/**
	 * Answers the command-line invocation defined for the receiver.
	 *
	 * @return String
	 */
	public String getToolCommand();

	/**
	 * Sets the command-line invocation command defined for this tool.
	 *
	 * @return boolean  if <code>true</code>, then the tool command was modified
	 */
	public boolean setToolCommand(String command);

	/**
	 * Returns command line pattern for this tool
	 * @return String
	 */
	public String getCommandLinePattern();

	/**
	 * Sets the command line pattern for this tool
	 */
	public void setCommandLinePattern(String pattern);

	/**
	 * Returns the command line generator specified for this tool
	 * @return IManagedCommandLineGenerator
	 */
	public IManagedCommandLineGenerator getCommandLineGenerator();

	/**
	 * Returns a class instance that implements an interface to generate
	 * source-level dependencies for the tool specified in the argument.
	 * This method may return <code>null</code> in which case, the receiver
	 * should assume that the tool does not require dependency information
	 * when the project is built.
	 *
	 * @return IManagedDependencyGenerator
	 * @deprecated - use getDependencyGeneratorForExtension or IInputType method
	 */
	@Deprecated
	public IManagedDependencyGenerator getDependencyGenerator();

	/**
	 * Returns a class instance that implements an interface to generate
	 * source-level dependencies for the tool specified in the argument.
	 * This method may return <code>null</code> in which case, the receiver
	 * should assume that the tool does not require dependency information
	 * when the project is built.
	 *
	 * @param sourceExt  source file extension
	 * @return IManagedDependencyGeneratorType
	 */
	public IManagedDependencyGeneratorType getDependencyGeneratorForExtension(String sourceExt);

	/**
	 * Returns an array of command line arguments that have been specified for
	 * the tool.
	 * The flags contain build macros resolved to the makefile format.
	 * That is if a user has chosen to expand all macros in the buildfile,
	 * the flags contain all macro references resolved, otherwise, if a user has
	 * chosen to keep the environment build macros unresolved, the flags contain
	 * the environment macro references converted to the buildfile variable format,
	 * all other macro references are resolved
	 *
	 * @deprecated - use getToolCommandFlags instead
	 */
	@Deprecated
	public String[] getCommandFlags() throws BuildException;

	/**
	 * Returns the command line arguments that have been specified for
	 * the tool.
	 * The string contains build macros resolved to the makefile format.
	 * That is if a user has chosen to expand all macros in the buildfile,
	 * the string contains all macro references resolved, otherwise, if a user has
	 * chosen to keep the environment build macros unresolved, the string contains
	 * the environment macro references converted to the buildfile variable format,
	 * all other macro references are resolved
	 *
	 * @return String
	 *
	 * @deprecated - use getToolCommandFlagsString instead
	 */
	@Deprecated
	public String getToolFlags() throws BuildException;

	/**
	 * Returns an array of command line arguments that have been specified for
	 * the tool.
	 * The flags contain build macros resolved to the makefile format.
	 * That is if a user has chosen to expand all macros in the buildfile,
	 * the flags contain all macro references resolved, otherwise, if a user has
	 * chosen to keep the environment build macros unresolved, the flags contain
	 * the environment macro references converted to the buildfile variable format,
	 * all other macro references are resolved
	 */
	public String[] getToolCommandFlags(IPath inputFileLocation, IPath outputFileLocation) throws BuildException;

	/**
	 * Returns the command line arguments that have been specified for
	 * the tool.
	 * The string contains build macros resolved to the makefile format.
	 * That is if a user has chosen to expand all macros in the buildfile,
	 * the string contains all macro references resolved, otherwise, if a user has
	 * chosen to keep the environment build macros unresolved, the string contains
	 * the environment macro references converted to the buildfile variable format,
	 * all other macro references are resolved
	 */
	public String getToolCommandFlagsString(IPath inputFileLocation, IPath outputFileLocation) throws BuildException;

	/**
	 * Options are organized into categories for UI purposes.
	 * These categories are organized into a tree.  This is the root
	 * of that tree.
	 *
	 * @return IOptionCategory
	 */
	public IOptionCategory getTopOptionCategory();

	/**
	 * Return <code>true</code> if the receiver builds files with the
	 * specified extension, else <code>false</code>.
	 *
	 * @param extension file extension of the source
	 * @return boolean
	 */
	public boolean buildsFileType(String extension);

	/**
	 * Return <code>true</code> if the receiver uses files with the
	 * specified extension as input, else <code>false</code>.  This
	 * returns true for a superset of the extensions that buildFileType
	 * returns true for - it includes secondary inputs.
	 *
	 * @param extension file extension of the source
	 * @return boolean
	 */
	public boolean isInputFileType(String extension);

	/**
	 * Answers <code>true</code> if the tool considers the file extension to be
	 * one associated with a header file.
	 *
	 * @param ext file extension of the source
	 * @return boolean
	 */
	public boolean isHeaderFile(String ext);

	/**
	 * Answers <code>true</code> if the receiver builds a file with the extension specified
	 * in the argument, else <code>false</code>.
	 *
	 * @param outputExtension extension of the file being produced by a tool
	 * @return boolean
	 */
	public boolean producesFileType(String outputExtension);

	/**
	 * Returns <code>true</code> if this tool has changes that need to
	 * be saved in the project file, else <code>false</code>.
	 *
	 * @return boolean
	 */
	public boolean isDirty();

	/**
	 * Sets the element's "dirty" (have I been modified?) flag.
	 */
	public void setDirty(boolean isDirty);

	/**
	 * Returns <code>true</code> if this tool was loaded from a manifest file,
	 * and <code>false</code> if it was loaded from a project (.cdtbuild) file.
	 *
	 * @return boolean
	 */
	public boolean isExtensionElement();

	/**
	 * Returns the 'versionsSupported' of this tool
	 *
	 * @return String
	 */
	public String getVersionsSupported();

	/**
	 * Returns the 'convertToId' of this tool
	 *
	 * @return String
	 */
	public String getConvertToId();

	/**
	 * Sets the 'versionsSupported' attribute of the tool.
	 */
	public void setVersionsSupported(String versionsSupported);

	/**
	 * Sets the 'convertToId' attribute of the tool.
	 */
	public void setConvertToId(String convertToId);

	/**
	 * Returns an array of the Environment Build Path variable descriptors
	 *
	 * @return IEnvVarBuildPath[]
	 */
	public IEnvVarBuildPath[] getEnvVarBuildPaths();

	/**
	 * Returns an IOptionPathConverter implementation for this tool
	 * or null, if no conversion is required
	 */
	public IOptionPathConverter getOptionPathConverter();

	/**
	 * An argument file format specifies the syntax of how argument files
	 * (or command files) can be passed to a command line tool to workaround
	 * limitations in the command line length limit.
	 *
	 * The argument file format is a string including the "${file}" macro, which
	 * will be replaced automatically by the path of the argument file, and passed
	 * as argument to the command line tool.
	 *
	 * @return the argument file format, or null if argument files are not supported
	 * @since 9.6
	 */
	public String getArgumentFileFormat();

	CLanguageData getCLanguageData(IInputType type);

	CLanguageData[] getCLanguageDatas();

	IInputType getInputTypeForCLanguageData(CLanguageData data);

	IResourceInfo getParentResourceInfo();

	IInputType getEditableInputType(IInputType base);

	IOutputType getEditableOutputType(IOutputType base);

	boolean isEnabled();

	boolean supportsBuild(boolean managed);

	boolean matches(ITool tool);

	boolean isSystemObject();

	/**
	 * @since 8.4
	 */
	boolean isHidden();

	/**
	 * @since 8.4
	 */
	void setHidden(boolean hidden);

	String getUniqueRealName();

	/**
	 * @since 9.4
	 */
	default public String[] getExtraFlags(int optionType) {
		return new String[0];
	}
}
