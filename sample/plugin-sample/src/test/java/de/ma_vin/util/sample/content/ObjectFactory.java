package de.ma_vin.util.sample.content;

import de.ma_vin.util.sample.content.dto.domain.DerivedFromDomainDto;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import de.ma_vin.util.sample.content.dao.dao.OnlyDaoDao;
import de.ma_vin.util.sample.content.dao.domain.dao.DomainAndDaoDao;
import de.ma_vin.util.sample.content.dao.filtering.*;
import de.ma_vin.util.sample.content.dao.multi.*;
import de.ma_vin.util.sample.content.dao.multi.indirect.*;
import de.ma_vin.util.sample.content.dao.parent.*;
import de.ma_vin.util.sample.content.dao.single.*;
import de.ma_vin.util.sample.content.dao.single.indirect.*;
import de.ma_vin.util.sample.content.domain.domain.OnlyDomain;
import de.ma_vin.util.sample.content.domain.domain.dao.DomainAndDao;
import de.ma_vin.util.sample.content.domain.domain.dto.DomainAndDto;
import de.ma_vin.util.sample.content.domain.filtering.*;
import de.ma_vin.util.sample.content.domain.multi.*;
import de.ma_vin.util.sample.content.domain.multi.indirect.*;
import de.ma_vin.util.sample.content.domain.parent.*;
import de.ma_vin.util.sample.content.domain.single.*;
import de.ma_vin.util.sample.content.domain.single.indirect.*;
import de.ma_vin.util.sample.content.dto.domain.dto.DomainAndDtoDto;
import de.ma_vin.util.sample.content.dto.dto.OnlyDtoDto;
import de.ma_vin.util.sample.content.dto.filtering.*;
import de.ma_vin.util.sample.content.dto.multi.*;
import de.ma_vin.util.sample.content.dto.multi.indirect.*;
import de.ma_vin.util.sample.content.dto.parent.*;
import de.ma_vin.util.sample.content.dto.single.*;
import de.ma_vin.util.sample.content.dto.single.indirect.*;
import de.ma_vin.util.sample.extending.ExtendedExtendingClassDto;
import de.ma_vin.util.sample.extending.ExtendedMultiRefTwoParents;
import de.ma_vin.util.sample.extending.ExtendedSingleRefOneParentDao;
import de.ma_vin.util.sample.given.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ObjectFactory {
    private static long maxId = 0l;
    private static Map<String, IIdentifiableDao> createdDaoObjects = new HashMap<>();
    private static Map<String, IIdentifiable> createdDomainObjects = new HashMap<>();
    private static Map<String, ITransportable> createdDtoObjects = new HashMap<>();

    public static void initObjectFactory() {
        maxId = 0l;
        createdDaoObjects.clear();
        createdDomainObjects.clear();
        createdDtoObjects.clear();
    }

    private static void updateMaxId(long id) {
        if (maxId < id) {
            maxId = id;
        }
    }

    public static long getNextId() {
        maxId++;
        return maxId;
    }

    private static void setId(IIdentifiableDao dao, long id) {
        dao.setId(id);
        updateMaxId(id);
    }

    private static void setId(IIdentifiable domain, long id, String prefix) {
        domain.setIdentification(IdGenerator.generateIdentification(id, prefix));
        updateMaxId(id);
    }

    private static void setId(ITransportable dto, long id, String prefix) {
        dto.setIdentification(IdGenerator.generateIdentification(id, prefix));
        updateMaxId(id);
    }

    public static void addToCreatedMap(IIdentifiableDao dao) {
        createdDaoObjects.put(dao.getIdentification(), dao);
    }

    public static void addToCreatedMap(IIdentifiable domain) {
        createdDomainObjects.put(domain.getIdentification(), domain);
    }

    public static void addToCreatedMap(ITransportable dto) {
        createdDtoObjects.put(dto.getIdentification(), dto);
    }

    public static RootDao createRootDao(long id) {
        RootDao result = new RootDao();
        setId(result, id);

        result.setRootName(String.format("RootName_%d", id));
        result.setDescription(String.format("Description_%d", id));

        result.setMultiRef(new ArrayList<>());
        result.setAnotherMultiRef(new ArrayList<>());
        result.setMultiRefIndirectParent(new ArrayList<>());
        result.setMultiRefIndirectOtherParent(new ArrayList<>());
        result.setMultiRefIndirectSelfReference(new ArrayList<>());
        result.setExtending(new ArrayList<>());

        return result;
    }

    public static RootDao createRootDaoWithChildren(long id) {
        RootDao result = createRootDao(id);

        result.setSingleRef(createSingleRefOneParentDaoWithChildren(getNextId()));
        result.getSingleRef().setParentRoot(result);
        result.setAnotherSingleRef(createSingleRefTwoParentsDao(getNextId()));
        result.getAnotherSingleRef().setParentRoot(result);

        MultiRefOneParentDao multiRefOneParentDao = createMultiRefOneParentDaoWithChildren(getNextId());
        multiRefOneParentDao.setParentRoot(result);
        result.getMultiRef().add(multiRefOneParentDao);

        MultiRefTwoParentsDao multiRefTwoParentsDao = createMultiRefTwoParentsDao(getNextId());
        multiRefTwoParentsDao.setParentRoot(result);
        result.getAnotherMultiRef().add(multiRefTwoParentsDao);

        result.setSingleRefIndirectParent(createSingleRefIndirectParentDao(getNextId()));
        result.getSingleRefIndirectParent().setParentRoot(result);
        addToCreatedMap(result.getSingleRefIndirectParent());

        result.setSingleRefIndirectOtherParent(createSingleRefOtherIndirectParentDaoWithChildren(getNextId()
                , result.getSingleRefIndirectParent().getIdentification()));
        result.getSingleRefIndirectOtherParent().setParentRoot(result);

        MultiRefIndirectParentDao multiRefIndirectParentDao = createMultiRefIndirectParentDao(getNextId());
        multiRefIndirectParentDao.setParentRoot(result);
        result.getMultiRefIndirectParent().add(multiRefIndirectParentDao);
        addToCreatedMap(multiRefIndirectParentDao);

        MultiRefIndirectSelfReferenceDao multiRefIndirectSelfReferenceDao = createMultiRefIndirectSelfReferenceDao(getNextId());
        MultiRefIndirectSelfReferenceDao subMultiRefIndirectSelfReferenceDao = createMultiRefIndirectSelfReferenceDao(getNextId());
        result.getMultiRefIndirectSelfReference().add(multiRefIndirectSelfReferenceDao);
        result.getMultiRefIndirectSelfReference().add(subMultiRefIndirectSelfReferenceDao);
        MultiRefIndirectSelfReferenceToMultiRefIndirectSelfReferenceDao selfConnection = new MultiRefIndirectSelfReferenceToMultiRefIndirectSelfReferenceDao();
        selfConnection.setMultiRefIndirectSelfReference(multiRefIndirectSelfReferenceDao);
        selfConnection.setSubMultiRefIndirectSelfReference(subMultiRefIndirectSelfReferenceDao);
        multiRefIndirectSelfReferenceDao.getSelfRefs().add(selfConnection);

        MultiRefOtherIndirectParentDao multiRefOtherIndirectParentDao
                = createMultiRefOtherIndirectParentDaoWithChildren(getNextId(), multiRefIndirectParentDao.getIdentification());
        multiRefOtherIndirectParentDao.setParentRoot(result);
        result.getMultiRefIndirectOtherParent().add(multiRefOtherIndirectParentDao);

        ExtendingClassDao extendingClassDao = createExtendingClassDao(getNextId());
        extendingClassDao.setParentRoot(result);
        result.getExtending().add(extendingClassDao);

        result.setFiltering(createSomeFilteringOwnerDaoWithChildren(getNextId()));
        result.setNonOwnerFiltering(createSomeDifferentFilteringNotOwnerDaoWithChildren(getNextId()));

        result.setExt(createRootExtDao(getNextId()));

        return result;
    }

    public static RootExtDao createRootExtDao(long id) {
        RootExtDao result = new RootExtDao();
        setId(result, id);

        result.setExtendedInfo(String.format("ExtendedInfo_%d", id));
        result.setSomeEnum(AnyEnumType.ENUM_VALUE_A);
        result.setSomeInteger(Integer.valueOf("" + id));
        result.setSomeCustom(new CustomType(String.format("SomeCustom_%d", id)));
        result.setOnlyDao(String.format("OnlyDao_%d", id));
        result.setDaoAndDomain(String.format("DaoAndDomain_%d", id));
        result.setTextWithDaoInfo(String.format("TextWithDaoInfo_%d", id));
        result.setNumberWithDaoInfo(Double.valueOf("" + id));
        result.setDaoEnum(AnyEnumType.ENUM_VALUE_B);
        result.setDaoEnumWithText(AnyEnumType.ENUM_VALUE_C);
        result.setSomeName(String.format("SomeName_%d", id));

        return result;
    }

    public static SingleRefOneParentDao createSingleRefOneParentDao(long id) {
        ExtendedSingleRefOneParentDao result = new ExtendedSingleRefOneParentDao();
        setId(result, id);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static SingleRefOneParentDao createSingleRefOneParentDaoWithChildren(long id) {
        SingleRefOneParentDao result = createSingleRefOneParentDao(id);
        result.setSingleRef(createSingleRefTwoParentsDao(getNextId()));
        result.getSingleRef().setParentSingleRefOneParent(result);
        return result;
    }

    public static SingleRefTwoParentsDao createSingleRefTwoParentsDao(long id) {
        SingleRefTwoParentsDao result = new SingleRefTwoParentsDao();
        setId(result, id);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static MultiRefOneParentDao createMultiRefOneParentDao(long id) {
        MultiRefOneParentDao result = new MultiRefOneParentDao();
        setId(result, id);
        result.setDescription(String.format("Description_%d", id));
        result.setMultiRef(new ArrayList<>());
        return result;
    }

    public static MultiRefOneParentDao createMultiRefOneParentDaoWithChildren(long id) {
        MultiRefOneParentDao result = createMultiRefOneParentDao(id);
        MultiRefTwoParentsDao multiRefTwoParentsDao = createMultiRefTwoParentsDao(getNextId());
        multiRefTwoParentsDao.setParentMultiRefOneParent(result);
        result.getMultiRef().add(multiRefTwoParentsDao);
        return result;
    }

    public static MultiRefTwoParentsDao createMultiRefTwoParentsDao(long id) {
        MultiRefTwoParentsDao result = new MultiRefTwoParentsDao();
        setId(result, id);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static SingleRefOtherIndirectParentDao createSingleRefOtherIndirectParentDao(long id) {
        SingleRefOtherIndirectParentDao result = new SingleRefOtherIndirectParentDao();
        setId(result, id);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static SingleRefOtherIndirectParentDao createSingleRefOtherIndirectParentDaoWithChildren(long id, String... identificationsToUse) {
        SingleRefOtherIndirectParentDao result = createSingleRefOtherIndirectParentDao(id);
        for (String identification : identificationsToUse) {
            if (createdDaoObjects.containsKey(identification) && createdDaoObjects.get(identification) instanceof SingleRefIndirectParentDao) {
                result.setSingleIndirectRef((SingleRefIndirectParentDao) createdDaoObjects.get(identification));
                break;
            }
        }
        return result;
    }

    public static SingleRefIndirectParentDao createSingleRefIndirectParentDao(long id) {
        SingleRefIndirectParentDao result = new SingleRefIndirectParentDao();
        setId(result, id);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static MultiRefOtherIndirectParentDao createMultiRefOtherIndirectParentDao(long id) {
        MultiRefOtherIndirectParentDao result = new MultiRefOtherIndirectParentDao();
        setId(result, id);
        result.setDescription(String.format("Description_%d", id));
        result.setMultiIndirectRef(new ArrayList<>());
        return result;
    }

    public static MultiRefOtherIndirectParentDao createMultiRefOtherIndirectParentDaoWithChildren(long id, String... identificationsToUse) {
        MultiRefOtherIndirectParentDao result = createMultiRefOtherIndirectParentDao(id);
        for (String identification : identificationsToUse) {
            if (createdDaoObjects.containsKey(identification) && createdDaoObjects.get(identification) instanceof MultiRefIndirectParentDao) {
                MultiRefOtherIndirectParentToMultiRefIndirectParentDao connection = new MultiRefOtherIndirectParentToMultiRefIndirectParentDao();
                connection.setMultiRefOtherIndirectParent(result);
                connection.setMultiRefIndirectParent((MultiRefIndirectParentDao) createdDaoObjects.get(identification));
                result.getMultiIndirectRef().add(connection);
                break;
            }
        }
        return result;
    }

    public static MultiRefIndirectParentDao createMultiRefIndirectParentDao(long id) {
        MultiRefIndirectParentDao result = new MultiRefIndirectParentDao();
        setId(result, id);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static MultiRefIndirectSelfReferenceDao createMultiRefIndirectSelfReferenceDao(long id) {
        MultiRefIndirectSelfReferenceDao result = new MultiRefIndirectSelfReferenceDao();
        result.setSelfRefs(new ArrayList<>());
        setId(result, id);
        return result;
    }

    public static ExtendingClassDao createExtendingClassDao(long id) {
        ExtendingClassDao result = new ExtendingClassDao();
        setId(result, id);
        result.setDescription(String.format("Description_%d", id));
        result.setAdditionalDescription(String.format("AdditionalDescription_%d", id));
        return result;
    }

    public static SomeFilteringOwnerDao createSomeFilteringOwnerDao(long id) {
        SomeFilteringOwnerDao result = new SomeFilteringOwnerDao();
        setId(result, id);
        result.setAggFiltered(new ArrayList<>());
        result.setAggFilteredOnlyDaoField(new ArrayList<>());
        result.setAggFilteredNotOwner(new ArrayList<>());
        result.setAggFilteredOnlyDaoFieldNotOwner(new ArrayList<>());
        return result;
    }

    public static SomeFilteringOwnerDao createSomeFilteringOwnerDaoWithChildren(long id) {
        SomeFilteringOwnerDao result = createSomeFilteringOwnerDao(id);
        result.getAggFiltered().add(createFilteredDao(getNextId(), AnyEnumType.ENUM_VALUE_A));
        result.getAggFiltered().add(createFilteredDao(getNextId(), AnyEnumType.ENUM_VALUE_B));
        result.getAggFiltered().add(createFilteredDao(getNextId(), AnyEnumType.ENUM_VALUE_C));

        SomeFilteringOwnerToFilteredNotOwnerDao connectionA = new SomeFilteringOwnerToFilteredNotOwnerDao();
        connectionA.setSomeFilteringOwner(result);
        connectionA.setFilteredNotOwner(createFilteredNotOwnerDao(getNextId(), AnyEnumType.ENUM_VALUE_A));
        result.getAggFilteredNotOwner().add(connectionA);
        SomeFilteringOwnerToFilteredNotOwnerDao connectionB = new SomeFilteringOwnerToFilteredNotOwnerDao();
        connectionB.setSomeFilteringOwner(result);
        connectionB.setFilteredNotOwner(createFilteredNotOwnerDao(getNextId(), AnyEnumType.ENUM_VALUE_B));
        result.getAggFilteredNotOwner().add(connectionB);
        SomeFilteringOwnerToFilteredNotOwnerDao connectionC = new SomeFilteringOwnerToFilteredNotOwnerDao();
        connectionC.setSomeFilteringOwner(result);
        connectionC.setFilteredNotOwner(createFilteredNotOwnerDao(getNextId(), AnyEnumType.ENUM_VALUE_C));
        result.getAggFilteredNotOwner().add(connectionC);

        result.getAggFilteredOnlyDaoField().add(createFilteredOnlyDaoFieldDao(getNextId(), AnyEnumType.ENUM_VALUE_A));
        result.getAggFilteredOnlyDaoField().add(createFilteredOnlyDaoFieldDao(getNextId(), AnyEnumType.ENUM_VALUE_B));
        result.getAggFilteredOnlyDaoField().add(createFilteredOnlyDaoFieldDao(getNextId(), AnyEnumType.ENUM_VALUE_C));

        SomeFilteringOwnerToFilteredOnlyDaoFieldNotOwnerDao onlyDaoConnectionA = new SomeFilteringOwnerToFilteredOnlyDaoFieldNotOwnerDao();
        onlyDaoConnectionA.setSomeFilteringOwner(result);
        onlyDaoConnectionA.setFilteredOnlyDaoFieldNotOwner(createFilteredOnlyDaoFieldNotOwnerDao(getNextId(), AnyEnumType.ENUM_VALUE_A));
        result.getAggFilteredOnlyDaoFieldNotOwner().add(onlyDaoConnectionA);
        SomeFilteringOwnerToFilteredOnlyDaoFieldNotOwnerDao onlyDaoConnectionB = new SomeFilteringOwnerToFilteredOnlyDaoFieldNotOwnerDao();
        onlyDaoConnectionB.setSomeFilteringOwner(result);
        onlyDaoConnectionB.setFilteredOnlyDaoFieldNotOwner(createFilteredOnlyDaoFieldNotOwnerDao(getNextId(), AnyEnumType.ENUM_VALUE_B));
        result.getAggFilteredOnlyDaoFieldNotOwner().add(onlyDaoConnectionB);
        SomeFilteringOwnerToFilteredOnlyDaoFieldNotOwnerDao onlyDaoConnectionC = new SomeFilteringOwnerToFilteredOnlyDaoFieldNotOwnerDao();
        onlyDaoConnectionC.setSomeFilteringOwner(result);
        onlyDaoConnectionC.setFilteredOnlyDaoFieldNotOwner(createFilteredOnlyDaoFieldNotOwnerDao(getNextId(), AnyEnumType.ENUM_VALUE_C));
        result.getAggFilteredOnlyDaoFieldNotOwner().add(onlyDaoConnectionC);
        return result;
    }

    public static FilteredDao createFilteredDao(long id, AnyEnumType enumType) {
        FilteredDao result = new FilteredDao();
        setId(result, id);
        result.setDescription(String.format("Description_%d", id));
        result.setSomeEnum(enumType);
        return result;
    }

    public static FilteredNotOwnerDao createFilteredNotOwnerDao(long id, AnyEnumType enumType) {
        FilteredNotOwnerDao result = new FilteredNotOwnerDao();
        setId(result, id);
        result.setDescriptionNotOwner(String.format("Description_%d", id));
        result.setSomeEnumNotOwner(enumType);
        return result;
    }

    public static FilteredOnlyDaoFieldDao createFilteredOnlyDaoFieldDao(long id, AnyEnumType enumType) {
        FilteredOnlyDaoFieldDao result = new FilteredOnlyDaoFieldDao();
        setId(result, id);
        result.setDescriptionOnlyDaoField(String.format("Description_%d", id));
        result.setSomeEnumOnlyDaoField(enumType);
        return result;
    }

    public static FilteredOnlyDaoFieldNotOwnerDao createFilteredOnlyDaoFieldNotOwnerDao(long id, AnyEnumType enumType) {
        FilteredOnlyDaoFieldNotOwnerDao result = new FilteredOnlyDaoFieldNotOwnerDao();
        setId(result, id);
        result.setDescriptionOnlyDaoFieldNotOwner(String.format("Description_%d", id));
        result.setSomeEnumOnlyDaoFieldNotOwner(enumType);
        return result;
    }

    public static SomeDifferentFilteringNotOwnerDao createSomeDifferentFilteringNotOwnerDao(long id) {
        SomeDifferentFilteringNotOwnerDao result = new SomeDifferentFilteringNotOwnerDao();
        setId(result, id);
        result.setAggFiltered(new ArrayList<>());
        return result;
    }

    public static SomeDifferentFilteringNotOwnerDao createSomeDifferentFilteringNotOwnerDaoWithChildren(long id) {
        SomeDifferentFilteringNotOwnerDao result = createSomeDifferentFilteringNotOwnerDao(id);

        SomeDifferentFilteringNotOwnerToFilteredDao connectionA = new SomeDifferentFilteringNotOwnerToFilteredDao();
        connectionA.setSomeDifferentFilteringNotOwner(result);
        connectionA.setFiltered(createFilteredDao(getNextId(), AnyEnumType.ENUM_VALUE_B));
        connectionA.setFilterAnyEnumType(AnyEnumType.ENUM_VALUE_A);
        result.getAggFiltered().add(connectionA);

        SomeDifferentFilteringNotOwnerToFilteredDao connectionB = new SomeDifferentFilteringNotOwnerToFilteredDao();
        connectionB.setSomeDifferentFilteringNotOwner(result);
        connectionB.setFiltered(createFilteredDao(getNextId(), AnyEnumType.ENUM_VALUE_C));
        connectionB.setFilterAnyEnumType(AnyEnumType.ENUM_VALUE_B);
        result.getAggFiltered().add(connectionB);

        SomeDifferentFilteringNotOwnerToFilteredDao connectionC = new SomeDifferentFilteringNotOwnerToFilteredDao();
        connectionC.setSomeDifferentFilteringNotOwner(result);
        connectionC.setFiltered(createFilteredDao(getNextId(), AnyEnumType.ENUM_VALUE_A));
        connectionC.setFilterAnyEnumType(AnyEnumType.ENUM_VALUE_C);
        result.getAggFiltered().add(connectionC);

        return result;
    }

    public static DomainAndDaoDao createDomainAndDaoDao(long id) {
        DomainAndDaoDao result = new DomainAndDaoDao();
        setId(result, id);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static OnlyDaoDao createOnlyDaoDao(long id) {
        OnlyDaoDao result = new OnlyDaoDao();
        setId(result, id);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }


    public static Root createRoot(long id) {
        Root result = new Root();
        setId(result, id, Root.ID_PREFIX);

        result.setRootName(String.format("RootName_%d", id));
        result.setDescription(String.format("Description_%d", id));

        return result;
    }

    public static Root createRootWithChildren(long id) {
        Root result = createRoot(id);

        result.setSingleRef(createSingleRefOneParentWithChildren(getNextId()));
        result.setAnotherSingleRef(createSingleRefTwoParents(getNextId()));

        result.getMultiRef().add(createMultiRefOneParentWithChildren(getNextId()));
        result.getAnotherMultiRef().add(createMultiRefTwoParents(getNextId()));

        result.setSingleRefIndirectParent(createSingleRefIndirectParent(getNextId()));
        addToCreatedMap(result.getSingleRefIndirectParent());
        result.setSingleRefIndirectOtherParent(createSingleRefOtherIndirectParentWithChildren(getNextId()
                , result.getSingleRefIndirectParent().getIdentification()));

        MultiRefIndirectParent multiRefIndirectParent = createMultiRefIndirectParent(getNextId());
        result.getMultiRefIndirectParent().add(multiRefIndirectParent);
        addToCreatedMap(multiRefIndirectParent);
        result.getMultiRefIndirectOtherParent().add(createMultiRefOtherIndirectParentWithChildren(getNextId(), multiRefIndirectParent.getIdentification()));

        MultiRefIndirectSelfReference multiRefIndirectSelfReference = createMultiRefIndirectSelfReference(getNextId());
        MultiRefIndirectSelfReference subMultiRefIndirectSelfReference = createMultiRefIndirectSelfReference(getNextId());
        multiRefIndirectSelfReference.addSelfRefs(subMultiRefIndirectSelfReference);
        result.addMultiRefIndirectSelfReference(multiRefIndirectSelfReference);
        result.addMultiRefIndirectSelfReference(subMultiRefIndirectSelfReference);

        result.getExtending().add(createExtendingClass(getNextId()));

        result.setFiltering(createSomeFilteringOwnerWithChildren(getNextId()));
        result.setNonOwnerFiltering(createSomeDifferentFilteringNotOwnerWithChildren(getNextId()));

        result.setExt(createRootExt(getNextId()));

        return result;
    }

    public static RootExt createRootExt(long id) {
        RootExt result = new RootExt();
        setId(result, id, RootExt.ID_PREFIX);

        result.setExtendedInfo(String.format("ExtendedInfo_%d", id));
        result.setSomeEnum(AnyEnumType.ENUM_VALUE_A);
        result.setSomeInteger(Integer.valueOf("" + id));
        result.setSomeCustom(new CustomType(String.format("SomeCustom_%d", id)));
        result.setDaoAndDomain(String.format("DaoAndDomain_%d", id));
        result.setTextWithDaoInfo(String.format("TextWithDaoInfo_%d", id));
        result.setNumberWithDaoInfo(Double.valueOf("" + id));
        result.setDaoEnum(AnyEnumType.ENUM_VALUE_B);
        result.setDaoEnumWithText(AnyEnumType.ENUM_VALUE_C);
        result.setSomeName(String.format("SomeName_%d", id));

        return result;
    }

    public static SingleRefOneParent createSingleRefOneParent(long id) {
        SingleRefOneParent result = new SingleRefOneParent();
        setId(result, id, SingleRefOneParent.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static SingleRefOneParent createSingleRefOneParentWithChildren(long id) {
        SingleRefOneParent result = createSingleRefOneParent(id);
        result.setSingleRef(createSingleRefTwoParents(getNextId()));
        return result;
    }

    public static SingleRefTwoParents createSingleRefTwoParents(long id) {
        SingleRefTwoParents result = new SingleRefTwoParents();
        setId(result, id, SingleRefTwoParents.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static MultiRefOneParent createMultiRefOneParent(long id) {
        MultiRefOneParent result = new MultiRefOneParent();
        setId(result, id, MultiRefOneParent.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static MultiRefOneParent createMultiRefOneParentWithChildren(long id) {
        MultiRefOneParent result = createMultiRefOneParent(id);
        MultiRefTwoParents multiRefTwoParents = createMultiRefTwoParents(getNextId());
        result.getMultiRef().add(multiRefTwoParents);
        return result;
    }

    public static MultiRefTwoParents createMultiRefTwoParents(long id) {
        ExtendedMultiRefTwoParents result = new ExtendedMultiRefTwoParents();
        setId(result, id, MultiRefTwoParents.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static SingleRefOtherIndirectParent createSingleRefOtherIndirectParent(long id) {
        SingleRefOtherIndirectParent result = new SingleRefOtherIndirectParent();
        setId(result, id, SingleRefOtherIndirectParent.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static SingleRefOtherIndirectParent createSingleRefOtherIndirectParentWithChildren(long id, String... identificationsToUse) {
        SingleRefOtherIndirectParent result = createSingleRefOtherIndirectParent(id);
        for (String identification : identificationsToUse) {
            if (createdDomainObjects.containsKey(identification) && createdDomainObjects.get(identification) instanceof SingleRefIndirectParent) {
                result.setSingleIndirectRef((SingleRefIndirectParent) createdDomainObjects.get(identification));
                break;
            }
        }
        return result;
    }

    public static SingleRefIndirectParent createSingleRefIndirectParent(long id) {
        SingleRefIndirectParent result = new SingleRefIndirectParent();
        setId(result, id, SingleRefIndirectParent.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static MultiRefOtherIndirectParent createMultiRefOtherIndirectParent(long id) {
        MultiRefOtherIndirectParent result = new MultiRefOtherIndirectParent();
        setId(result, id, MultiRefOtherIndirectParent.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static MultiRefOtherIndirectParent createMultiRefOtherIndirectParentWithChildren(long id, String... identificationsToUse) {
        MultiRefOtherIndirectParent result = createMultiRefOtherIndirectParent(id);
        for (String identification : identificationsToUse) {
            if (createdDomainObjects.containsKey(identification) && createdDomainObjects.get(identification) instanceof MultiRefIndirectParent) {
                result.addMultiIndirectRef((MultiRefIndirectParent) createdDomainObjects.get(identification));
                break;
            }
        }
        return result;
    }

    public static MultiRefIndirectParent createMultiRefIndirectParent(long id) {
        MultiRefIndirectParent result = new MultiRefIndirectParent();
        setId(result, id, MultiRefIndirectParent.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static MultiRefIndirectSelfReference createMultiRefIndirectSelfReference(long id) {
        MultiRefIndirectSelfReference result = new MultiRefIndirectSelfReference();
        setId(result, id, MultiRefIndirectSelfReference.ID_PREFIX);
        return result;
    }

    public static ExtendingClass createExtendingClass(long id) {
        ExtendingClass result = new ExtendingClass();
        setId(result, id, ExtendingClass.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        result.setAdditionalDescription(String.format("AdditionalDescription_%d", id));
        return result;
    }

    public static SomeFilteringOwner createSomeFilteringOwner(long id) {
        SomeFilteringOwner result = new SomeFilteringOwner();
        setId(result, id, SomeFilteringOwner.ID_PREFIX);
        return result;
    }

    public static SomeFilteringOwner createSomeFilteringOwnerWithChildren(long id) {
        SomeFilteringOwner result = createSomeFilteringOwner(id);

        result.addFilterA(createFiltered(getNextId(), AnyEnumType.ENUM_VALUE_A));
        result.addFilterB(createFiltered(getNextId(), AnyEnumType.ENUM_VALUE_B));
        result.addFilterC(createFiltered(getNextId(), AnyEnumType.ENUM_VALUE_C));

        result.addFilterNotOwnerA(createFilteredNotOwner(getNextId(), AnyEnumType.ENUM_VALUE_A));
        result.addFilterNotOwnerB(createFilteredNotOwner(getNextId(), AnyEnumType.ENUM_VALUE_B));
        result.addFilterNotOwnerC(createFilteredNotOwner(getNextId(), AnyEnumType.ENUM_VALUE_C));

        result.addFilterDaoA(createFilteredOnlyDaoField(getNextId()));
        result.addFilterDaoB(createFilteredOnlyDaoField(getNextId()));
        result.addFilterDaoC(createFilteredOnlyDaoField(getNextId()));

        result.addFilterDaoNotOwnerA(createFilteredOnlyDaoFieldNotOwner(getNextId()));
        result.addFilterDaoNotOwnerB(createFilteredOnlyDaoFieldNotOwner(getNextId()));
        result.addFilterDaoNotOwnerC(createFilteredOnlyDaoFieldNotOwner(getNextId()));

        return result;
    }

    public static Filtered createFiltered(long id, AnyEnumType enumType) {
        Filtered result = new Filtered();
        setId(result, id, Filtered.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        result.setSomeEnum(enumType);
        return result;
    }

    public static FilteredNotOwner createFilteredNotOwner(long id, AnyEnumType enumType) {
        FilteredNotOwner result = new FilteredNotOwner();
        setId(result, id, FilteredNotOwner.ID_PREFIX);
        result.setDescriptionNotOwner(String.format("Description_%d", id));
        result.setSomeEnumNotOwner(enumType);
        return result;
    }

    public static FilteredOnlyDaoField createFilteredOnlyDaoField(long id) {
        FilteredOnlyDaoField result = new FilteredOnlyDaoField();
        setId(result, id, FilteredOnlyDaoField.ID_PREFIX);
        result.setDescriptionOnlyDaoField(String.format("Description_%d", id));
        return result;
    }

    public static FilteredOnlyDaoFieldNotOwner createFilteredOnlyDaoFieldNotOwner(long id) {
        FilteredOnlyDaoFieldNotOwner result = new FilteredOnlyDaoFieldNotOwner();
        setId(result, id, FilteredOnlyDaoFieldNotOwner.ID_PREFIX);
        result.setDescriptionOnlyDaoFieldNotOwner(String.format("Description_%d", id));
        return result;
    }

    public static SomeDifferentFilteringNotOwner createSomeDifferentFilteringNotOwner(long id) {
        SomeDifferentFilteringNotOwner result = new SomeDifferentFilteringNotOwner();
        setId(result, id, SomeDifferentFilteringNotOwner.ID_PREFIX);
        return result;
    }

    public static SomeDifferentFilteringNotOwner createSomeDifferentFilteringNotOwnerWithChildren(long id) {
        SomeDifferentFilteringNotOwner result = createSomeDifferentFilteringNotOwner(id);

        result.addFilterA(createFiltered(getNextId(), AnyEnumType.ENUM_VALUE_B));
        result.addFilterB(createFiltered(getNextId(), AnyEnumType.ENUM_VALUE_C));
        result.addFilterC(createFiltered(getNextId(), AnyEnumType.ENUM_VALUE_A));

        return result;
    }

    public static DomainAndDao createDomainAndDao(long id) {
        DomainAndDao result = new DomainAndDao();
        setId(result, id, DomainAndDao.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static OnlyDomain createOnlyDomain(long id) {
        OnlyDomain result = new OnlyDomain();
        setId(result, id, OnlyDomain.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }


    public static RootDto createRootDto(long id) {
        RootDto result = new RootDto();
        setId(result, id, Root.ID_PREFIX);

        result.setRootName(String.format("RootName_%d", id));
        result.setDescription(String.format("Description_%d", id));

        return result;
    }

    public static RootDto createRootDtoWithChildren(long id) {
        RootDto result = createRootDto(id);

        result.setSingleRef(createSingleRefOneParentDtoWithChildren(getNextId()));
        result.setAnotherSingleRef(createSingleRefTwoParentsDto(getNextId()));

        result.setSingleRefIndirectParent(createSingleRefIndirectParentDto(getNextId()));
        result.setSingleRefIndirectOtherParent(createSingleRefOtherIndirectParentDtoWithChildren(getNextId()));

        result.setFiltering(createSomeFilteringOwnerDto(getNextId()));
        result.setNonOwnerFiltering(createSomeDifferentFilteringNotOwnerDto(getNextId()));

        result.setExt(createRootExtDto(getNextId()));

        return result;
    }

    public static RootExtDto createRootExtDto(long id) {
        RootExtDto result = new RootExtDto();
        setId(result, id, RootExt.ID_PREFIX);

        result.setExtendedInfo(String.format("ExtendedInfo_%d", id));
        result.setSomeEnum(AnyEnumType.ENUM_VALUE_A);
        result.setSomeInteger(Integer.valueOf("" + id));
        result.setSomeCustom(new CustomType(String.format("SomeCustom_%d", id)));
        result.setOnlyDto(String.format("OnlyDto_%d", id));
        result.setDtoAndDomain(String.format("DaoAndDomain_%d", id));
        result.setTextWithDaoInfo(String.format("TextWithDtoInfo_%d", id));
        result.setNumberWithDaoInfo(Double.valueOf("" + id).doubleValue());
        result.setDaoEnum(AnyEnumType.ENUM_VALUE_B);
        result.setDaoEnumWithText(AnyEnumType.ENUM_VALUE_C);
        result.setSomeName(String.format("SomeName_%d", id));

        return result;
    }

    public static SingleRefOneParentDto createSingleRefOneParentDto(long id) {
        SingleRefOneParentDto result = new SingleRefOneParentDto();
        setId(result, id, SingleRefOneParent.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static SingleRefOneParentDto createSingleRefOneParentDtoWithChildren(long id) {
        SingleRefOneParentDto result = createSingleRefOneParentDto(id);
        result.setSingleRef(createSingleRefTwoParentsDto(getNextId()));
        return result;
    }

    public static SingleRefTwoParentsDto createSingleRefTwoParentsDto(long id) {
        SingleRefTwoParentsDto result = new SingleRefTwoParentsDto();
        setId(result, id, SingleRefTwoParents.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static MultiRefOneParentDto createMultiRefOneParentDto(long id) {
        MultiRefOneParentDto result = new MultiRefOneParentDto();
        setId(result, id, MultiRefOneParent.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static MultiRefTwoParentsDto createMultiRefTwoParentsDto(long id) {
        MultiRefTwoParentsDto result = new MultiRefTwoParentsDto();
        setId(result, id, MultiRefTwoParents.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static SingleRefOtherIndirectParentDto createSingleRefOtherIndirectParentDto(long id) {
        SingleRefOtherIndirectParentDto result = new SingleRefOtherIndirectParentDto();
        setId(result, id, SingleRefOtherIndirectParent.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static SingleRefOtherIndirectParentDto createSingleRefOtherIndirectParentDtoWithChildren(long id, String... identificationsToUse) {
        SingleRefOtherIndirectParentDto result = createSingleRefOtherIndirectParentDto(id);
        for (String identification : identificationsToUse) {
            if (createdDtoObjects.containsKey(identification) && createdDtoObjects.get(identification) instanceof SingleRefIndirectParentDto) {
                result.setSingleIndirectRef((SingleRefIndirectParentDto) createdDtoObjects.get(identification));
                break;
            }
        }
        return result;
    }

    public static SingleRefIndirectParentDto createSingleRefIndirectParentDto(long id) {
        SingleRefIndirectParentDto result = new SingleRefIndirectParentDto();
        setId(result, id, SingleRefIndirectParent.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static MultiRefOtherIndirectParentDto createMultiRefOtherIndirectParentDto(long id) {
        MultiRefOtherIndirectParentDto result = new MultiRefOtherIndirectParentDto();
        setId(result, id, MultiRefOtherIndirectParent.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static MultiRefIndirectParentDto createMultiRefIndirectParentDto(long id) {
        MultiRefIndirectParentDto result = new MultiRefIndirectParentDto();
        setId(result, id, MultiRefIndirectParent.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static MultiRefIndirectSelfReferenceDto createMultiRefIndirectSelfReferenceDto(long id) {
        MultiRefIndirectSelfReferenceDto result = new MultiRefIndirectSelfReferenceDto();
        setId(result, id, MultiRefIndirectSelfReference.ID_PREFIX);
        return result;
    }

    public static ExtendingClassDto createExtendingClassDto(long id) {
        ExtendedExtendingClassDto result = new ExtendedExtendingClassDto();
        setId(result, id, ExtendingClass.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        result.setAdditionalDescription(String.format("AdditionalDescription_%d", id));
        return result;
    }

    public static SomeFilteringOwnerDto createSomeFilteringOwnerDto(long id) {
        SomeFilteringOwnerDto result = new SomeFilteringOwnerDto();
        setId(result, id, SomeFilteringOwner.ID_PREFIX);
        return result;
    }

    public static FilteredDto createFilteredDto(long id, AnyEnumType enumType) {
        FilteredDto result = new FilteredDto();
        setId(result, id, Filtered.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        result.setSomeEnum(enumType);
        return result;
    }

    public static FilteredOnlyDaoFieldDto createFilteredOnlyDaoFieldDto(long id, AnyEnumType enumType) {
        FilteredOnlyDaoFieldDto result = new FilteredOnlyDaoFieldDto();
        setId(result, id, FilteredOnlyDaoField.ID_PREFIX);
        result.setDescriptionOnlyDaoField(String.format("Description_%d", id));
        return result;
    }

    public static SomeDifferentFilteringNotOwnerDto createSomeDifferentFilteringNotOwnerDto(long id) {
        SomeDifferentFilteringNotOwnerDto result = new SomeDifferentFilteringNotOwnerDto();
        setId(result, id, SomeDifferentFilteringNotOwner.ID_PREFIX);
        return result;
    }

    public static DomainAndDtoDto createDomainAndDtoDto(long id) {
        DomainAndDtoDto result = new DomainAndDtoDto();
        setId(result, id, DomainAndDto.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static DomainAndDto createDomainAndDto(long id) {
        DomainAndDto result = new DomainAndDto();
        setId(result, id, DomainAndDto.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static OnlyDtoDto createOnlyDtoDto(long id) {
        OnlyDtoDto result = new OnlyDtoDto();
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static DerivedFromDomainDto createDerivedFromDomainDto(long id) {
        DerivedFromDomainDto result = new DerivedFromDomainDto();
        setId(result, id, SingleRefOneParent.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static DerivedFromDomainDto createDerivedFromDomainDtoWithChildren(long id) {
        DerivedFromDomainDto result = createDerivedFromDomainDto(id);
        result.setSingleRef(createSingleRefTwoParentsDto(getNextId()));
        return result;
    }
}
