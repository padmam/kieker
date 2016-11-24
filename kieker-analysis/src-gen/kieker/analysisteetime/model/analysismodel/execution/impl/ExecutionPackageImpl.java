/**
 */
package kieker.analysisteetime.model.analysismodel.execution.impl;

import kieker.analysisteetime.model.analysismodel.AnalysismodelPackage;

import kieker.analysisteetime.model.analysismodel.architecture.ArchitecturePackage;

import kieker.analysisteetime.model.analysismodel.architecture.impl.ArchitecturePackageImpl;

import kieker.analysisteetime.model.analysismodel.deployment.DeploymentPackage;

import kieker.analysisteetime.model.analysismodel.deployment.impl.DeploymentPackageImpl;

import kieker.analysisteetime.model.analysismodel.execution.AggregatedInvocation;
import kieker.analysisteetime.model.analysismodel.execution.ExecutionFactory;
import kieker.analysisteetime.model.analysismodel.execution.ExecutionPackage;
import kieker.analysisteetime.model.analysismodel.execution.ExecutionRoot;

import kieker.analysisteetime.model.analysismodel.impl.AnalysismodelPackageImpl;

import kieker.analysisteetime.model.analysismodel.statistics.StatisticsPackage;

import kieker.analysisteetime.model.analysismodel.statistics.impl.StatisticsPackageImpl;

import kieker.analysisteetime.model.analysismodel.trace.TracePackage;

import kieker.analysisteetime.model.analysismodel.trace.impl.TracePackageImpl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ExecutionPackageImpl extends EPackageImpl implements ExecutionPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass executionRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass aggregatedInvocationEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see kieker.analysisteetime.model.analysismodel.execution.ExecutionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ExecutionPackageImpl() {
		super(eNS_URI, ExecutionFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link ExecutionPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ExecutionPackage init() {
		if (isInited) return (ExecutionPackage)EPackage.Registry.INSTANCE.getEPackage(ExecutionPackage.eNS_URI);

		// Obtain or create and register package
		ExecutionPackageImpl theExecutionPackage = (ExecutionPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ExecutionPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ExecutionPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		AnalysismodelPackageImpl theAnalysismodelPackage = (AnalysismodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AnalysismodelPackage.eNS_URI) instanceof AnalysismodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AnalysismodelPackage.eNS_URI) : AnalysismodelPackage.eINSTANCE);
		StatisticsPackageImpl theStatisticsPackage = (StatisticsPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(StatisticsPackage.eNS_URI) instanceof StatisticsPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(StatisticsPackage.eNS_URI) : StatisticsPackage.eINSTANCE);
		ArchitecturePackageImpl theArchitecturePackage = (ArchitecturePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ArchitecturePackage.eNS_URI) instanceof ArchitecturePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ArchitecturePackage.eNS_URI) : ArchitecturePackage.eINSTANCE);
		DeploymentPackageImpl theDeploymentPackage = (DeploymentPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DeploymentPackage.eNS_URI) instanceof DeploymentPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DeploymentPackage.eNS_URI) : DeploymentPackage.eINSTANCE);
		TracePackageImpl theTracePackage = (TracePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TracePackage.eNS_URI) instanceof TracePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TracePackage.eNS_URI) : TracePackage.eINSTANCE);

		// Create package meta-data objects
		theExecutionPackage.createPackageContents();
		theAnalysismodelPackage.createPackageContents();
		theStatisticsPackage.createPackageContents();
		theArchitecturePackage.createPackageContents();
		theDeploymentPackage.createPackageContents();
		theTracePackage.createPackageContents();

		// Initialize created meta-data
		theExecutionPackage.initializePackageContents();
		theAnalysismodelPackage.initializePackageContents();
		theStatisticsPackage.initializePackageContents();
		theArchitecturePackage.initializePackageContents();
		theDeploymentPackage.initializePackageContents();
		theTracePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theExecutionPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ExecutionPackage.eNS_URI, theExecutionPackage);
		return theExecutionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExecutionRoot() {
		return executionRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExecutionRoot_AggregatedInvocations() {
		return (EReference)executionRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAggregatedInvocation() {
		return aggregatedInvocationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAggregatedInvocation_Source() {
		return (EReference)aggregatedInvocationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAggregatedInvocation_Target() {
		return (EReference)aggregatedInvocationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAggregatedInvocation_ExecutionRoot() {
		return (EReference)aggregatedInvocationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExecutionFactory getExecutionFactory() {
		return (ExecutionFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		executionRootEClass = createEClass(EXECUTION_ROOT);
		createEReference(executionRootEClass, EXECUTION_ROOT__AGGREGATED_INVOCATIONS);

		aggregatedInvocationEClass = createEClass(AGGREGATED_INVOCATION);
		createEReference(aggregatedInvocationEClass, AGGREGATED_INVOCATION__SOURCE);
		createEReference(aggregatedInvocationEClass, AGGREGATED_INVOCATION__TARGET);
		createEReference(aggregatedInvocationEClass, AGGREGATED_INVOCATION__EXECUTION_ROOT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		DeploymentPackage theDeploymentPackage = (DeploymentPackage)EPackage.Registry.INSTANCE.getEPackage(DeploymentPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(executionRootEClass, ExecutionRoot.class, "ExecutionRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExecutionRoot_AggregatedInvocations(), this.getAggregatedInvocation(), this.getAggregatedInvocation_ExecutionRoot(), "aggregatedInvocations", null, 0, -1, ExecutionRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(aggregatedInvocationEClass, AggregatedInvocation.class, "AggregatedInvocation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAggregatedInvocation_Source(), theDeploymentPackage.getDeployedOperation(), null, "source", null, 0, 1, AggregatedInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAggregatedInvocation_Target(), theDeploymentPackage.getDeployedOperation(), null, "target", null, 0, 1, AggregatedInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAggregatedInvocation_ExecutionRoot(), this.getExecutionRoot(), this.getExecutionRoot_AggregatedInvocations(), "executionRoot", null, 0, 1, AggregatedInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //ExecutionPackageImpl
