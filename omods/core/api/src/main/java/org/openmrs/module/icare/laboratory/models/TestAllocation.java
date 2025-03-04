package org.openmrs.module.icare.laboratory.models;

// Generated Oct 7, 2020 12:48:40 PM by Hibernate Tools 5.2.10.Final

import org.hibernate.annotations.GenericGenerator;
import org.openmrs.*;

import javax.persistence.*;
import java.util.*;

/**
 * LbTestAllocation generated by hbm2java
 */
@Entity
@Table(name = "lb_test_allocation")
public class TestAllocation extends BaseOpenmrsData implements java.io.Serializable {
	
	@Id
	@GeneratedValue()
	@Column(name = "test_allocation_id", unique = true, nullable = false)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_id")
	private Device device;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "sample_id", referencedColumnName = "sample_id", nullable = false),
	        @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false) })
	private SampleOrder sampleOrder;
	
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sample_id")
	private Sample sample;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;*/
	
	@Column(name = "label", length = 65535)
	private String label;
	
	@ManyToOne
	@JoinColumn(name = "container_id")
	private Concept container;
	
	@ManyToOne
	@JoinColumn(name = "concept_id")
	private Concept testConcept;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "testAllocation")
	private List<TestAllocationStatus> testAllocationStatuses = new ArrayList<TestAllocationStatus>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "testAllocation")
	private List<Result> testAllocationResults = new ArrayList<Result>(0);
	
	public Concept getTestConcept() {
		return this.testConcept;
	}
	
	public void setTestConcept(Concept testConcept) {
		this.testConcept = testConcept;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public SampleOrder getSampleOrder() {
		return sampleOrder;
	}
	
	public Sample getSample() {
		return this.sampleOrder.getSample();
	}
	
	public void setSampleOrder(SampleOrder sampleOrder) {
		this.sampleOrder = sampleOrder;
	}
	
	public List<TestAllocationStatus> getTestAllocationStatuses() {
		return testAllocationStatuses;
	}
	
	public void setTestAllocationStatuses(List<TestAllocationStatus> testAllocationStatuses) {
		this.testAllocationStatuses = testAllocationStatuses;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public List<Result> getTestAllocationResults() {
		return testAllocationResults;
	}
	
	public void setTestAllocationResults(List<Result> testAllocationResults) {
		this.testAllocationResults = testAllocationResults;
	}
	
	public Concept getContainer() {
		return container;
	}
	
	public void setContainer(Concept container) {
		this.container = container;
	}
	
	public static TestAllocation fromMap(Map<String, Object> map) {
		Concept containerConcept = new Concept();
		containerConcept.setUuid(((Map<String, Object>) map.get("container")).get("uuid").toString());
		
		Concept testConcept = new Concept();
		if (map.get("concept") != null && ((Map<String, Object>) map.get("concept")).get("uuid") != null) {
			testConcept.setUuid(((Map<String, Object>) map.get("concept")).get("uuid").toString());
		}
		
		Order order = new Order();
		order.setUuid(((Map<String, Object>) map.get("order")).get("uuid").toString());
		
		Sample sample = new Sample();
		sample.setUuid(((Map<String, Object>) map.get("sample")).get("uuid").toString());
		
		SampleOrder sampleOrder = new SampleOrder();
		sampleOrder.setSample(sample);
		sampleOrder.setOrder(order);
		
		TestAllocation testAllocation = new TestAllocation();
		testAllocation.setLabel(map.get("label").toString());
		testAllocation.setContainer(containerConcept);
		testAllocation.setSampleOrder(sampleOrder);
		if (testConcept != null) {
			testAllocation.setTestConcept(testConcept);
		}
		
		return testAllocation;
	}
	
	public Map<String, Object> toMap() {
		Map<String, Object> testAllocationMap = new HashMap<String, Object>();
		testAllocationMap.put("uuid", this.getUuid());
		testAllocationMap.put("label", this.getLabel());
		
		if (this.getContainer() != null) {
			Map<String, Object> containerMap = new HashMap<String, Object>();
			containerMap.put("uuid", this.getContainer().getUuid());
			containerMap.put("display", this.getContainer().getDisplayString());
			testAllocationMap.put("container", containerMap);
		}
		
		if (this.getTestConcept() != null) {
			Map<String, Object> testConceptMap = new HashMap<String, Object>();
			testConceptMap.put("uuid", this.getTestConcept().getUuid());
			testConceptMap.put("display", this.getTestConcept().getDisplayString());
			Map<String, Object> datatype = new HashMap<>();
			datatype.put("uuid", this.getTestConcept().getDatatype().getUuid());
			datatype.put("name", this.getTestConcept().getDatatype().getName());
			datatype.put("display", this.getTestConcept().getDatatype().getName());
			datatype.put("description", this.getTestConcept().getDatatype().getDescription());
			testConceptMap.put("datatype", datatype);
			List<Map<String, Object>> mappings = new ArrayList<>();
			if (testConcept.getConceptMappings().size() > 0) {
				for(ConceptMap conceptMap: testConcept.getConceptMappings()) {
					ConceptReferenceTerm conceptReferenceTerm  = conceptMap.getConceptReferenceTerm();
					String sourceUuid = conceptReferenceTerm.getConceptSource().getUuid();
					Map<String, Object> mapping = new HashMap<>();
					Map<String, Object> conceptReference = new HashMap<>();
					conceptReference.put("uuid", conceptReferenceTerm.getUuid().toString());
					conceptReference.put("display", conceptReferenceTerm.getName().toString());
					conceptReference.put("name", conceptReferenceTerm.getName().toString());
					conceptReference.put("code", conceptReferenceTerm.getCode().toString());
					Map<String, Object> conceptSource =new HashMap<>();
					conceptSource.put("uuid", conceptReferenceTerm.getConceptSource().getUuid().toString());
					conceptSource.put("display", conceptReferenceTerm.getConceptSource().getName().toString());
					conceptReference.put("conceptSource",conceptSource);
					mapping.put("conceptReference", conceptReference);
					mappings.add(mapping);
				}
			} else {
				mappings = null;
			}
			testConceptMap.put("mappings", mappings);
			//			testConceptMap.put("names", this.getTestConcept().getNames());
			//			testConceptMap.put("shortNames", this.getTestConcept().getShortNames());
			testAllocationMap.put("concept", testConceptMap);
			testAllocationMap.put("parameter", testConceptMap);
		}
		
		List<Map<String, Object>> testAllocationStatusMap = new ArrayList<Map<String, Object>>();
		for (TestAllocationStatus status : this.getTestAllocationStatuses()) {
			testAllocationStatusMap.add(status.toMap());
		}
		testAllocationMap.put("statuses", testAllocationStatusMap);
		
		List<Map<String, Object>> resultssMap = new ArrayList<Map<String, Object>>();
		for (Result result : this.getTestAllocationResults()) {
			resultssMap.add(result.toMap());
		}
		testAllocationMap.put("results", resultssMap);
		Map<String, Object> order = new HashMap<String, Object>();
		order.put("uuid", this.sampleOrder.getOrder().getUuid());
		Map<String, Object> orderer = new HashMap<String, Object>();
		orderer.put("uuid", this.sampleOrder.getOrder().getOrderer().getUuid());
		orderer.put("name", this.sampleOrder.getOrder().getOrderer().getName());
		orderer.put("display", this.sampleOrder.getOrder().getOrderer().getName());
		order.put("orderer", orderer);
		order.put("orderNumber", this.sampleOrder.getOrder().getOrderNumber());
		Map<String, Object> orderConcept = new HashMap<>();
		orderConcept.put("uuid", this.sampleOrder.getOrder().getConcept().getUuid());
		orderConcept.put("display", this.sampleOrder.getOrder().getConcept().getDisplayString());
		List<Map<String, Object>> setMembers = new ArrayList<>();
		for (Concept setMember: this.sampleOrder.getOrder().getConcept().getSetMembers()) {
			Map<String, Object> member = new HashMap<>();
			member.put("uuid", setMember.getUuid());
			member.put("display", setMember.getDisplayString());
			setMembers.add(member);
		}
		orderConcept.put("setMembers", setMembers);
		order.put("concept", orderConcept);
		order.put("dateCreated", this.sampleOrder.getOrder().getDateCreated());
		order.put("dateActivated", this.sampleOrder.getOrder().getDateActivated());
		testAllocationMap.put("order", order);
		
		Map<String, Object> sample = new HashMap<String, Object>();
		sample.put("uuid", this.sampleOrder.getSample().getUuid());
		sample.put("label", this.sampleOrder.getSample().getLabel());
		testAllocationMap.put("sample", sample);
		testAllocationMap.put("isSetMember", this.getSampleOrder().getOrder().getConcept().getSetMembers().size() > 0 && this.getTestConcept().getUuid().toString() != this.getSampleOrder().getOrder().getConcept().getUuid().toString());
		return testAllocationMap;
	}
	
	public Device getDevice() {
		return device;
	}
	
	public void setDevice(Device device) {
		this.device = device;
	}
}
