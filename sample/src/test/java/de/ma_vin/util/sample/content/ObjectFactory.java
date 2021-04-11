package de.ma_vin.util.sample.content;

import de.ma_vin.ape.utils.generators.IdGenerator;
import de.ma_vin.util.sample.content.dao.*;
import de.ma_vin.util.sample.content.dao.dao.OnlyDaoDao;
import de.ma_vin.util.sample.content.dao.domain.dao.DomainAndDaoDao;
import de.ma_vin.util.sample.content.dao.filtering.*;
import de.ma_vin.util.sample.content.dao.multi.*;
import de.ma_vin.util.sample.content.dao.multi.indirect.*;
import de.ma_vin.util.sample.content.dao.parent.*;
import de.ma_vin.util.sample.content.dao.single.*;
import de.ma_vin.util.sample.content.dao.single.indirect.*;
import de.ma_vin.util.sample.content.domain.*;
import de.ma_vin.util.sample.content.domain.domain.OnlyDomain;
import de.ma_vin.util.sample.content.domain.domain.dao.DomainAndDao;
import de.ma_vin.util.sample.content.domain.domain.dto.DomainAndDto;
import de.ma_vin.util.sample.content.domain.filtering.*;
import de.ma_vin.util.sample.content.domain.multi.*;
import de.ma_vin.util.sample.content.domain.multi.indirect.*;
import de.ma_vin.util.sample.content.domain.parent.*;
import de.ma_vin.util.sample.content.domain.single.*;
import de.ma_vin.util.sample.content.domain.single.indirect.*;
import de.ma_vin.util.sample.content.dto.*;
import de.ma_vin.util.sample.content.dto.domain.dto.DomainAndDtoDto;
import de.ma_vin.util.sample.content.dto.dto.OnlyDtoDto;
import de.ma_vin.util.sample.content.dto.filtering.*;
import de.ma_vin.util.sample.content.dto.multi.*;
import de.ma_vin.util.sample.content.dto.multi.indirect.*;
import de.ma_vin.util.sample.content.dto.parent.*;
import de.ma_vin.util.sample.content.dto.single.*;
import de.ma_vin.util.sample.content.dto.single.indirect.*;
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

        result.setMultiRefs(new ArrayList<>());
        result.setAnotherMultiRefs(new ArrayList<>());
        result.setMultiRefIndirectParents(new ArrayList<>());
        result.setMultiRefIndirectOtherParents(new ArrayList<>());
        result.setExtendings(new ArrayList<>());

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
        result.getMultiRefs().add(multiRefOneParentDao);

        MultiRefTwoParentsDao multiRefTwoParentsDao = createMultiRefTwoParentsDao(getNextId());
        multiRefTwoParentsDao.setParentRoot(result);
        result.getAnotherMultiRefs().add(multiRefTwoParentsDao);

        result.setSingleRefIndirectParent(createSingleRefIndirectParentDao(getNextId()));
        result.getSingleRefIndirectParent().setParentRoot(result);
        addToCreatedMap(result.getSingleRefIndirectParent());

        result.setSingleRefIndirectOtherParent(createSingleRefOtherIndirectParentDaoWithChildren(getNextId()
                , result.getSingleRefIndirectParent().getIdentification()));
        result.getSingleRefIndirectOtherParent().setParentRoot(result);

        MultiRefIndirectParentDao multiRefIndirectParentDao = createMultiRefIndirectParentDao(getNextId());
        multiRefIndirectParentDao.setParentRoot(result);
        result.getMultiRefIndirectParents().add(multiRefIndirectParentDao);
        addToCreatedMap(multiRefIndirectParentDao);

        MultiRefOtherIndirectParentDao multiRefOtherIndirectParentDao
                = createMultiRefOtherIndirectParentDaoWithChildren(getNextId(), multiRefIndirectParentDao.getIdentification());
        multiRefOtherIndirectParentDao.setParentRoot(result);
        result.getMultiRefIndirectOtherParents().add(multiRefOtherIndirectParentDao);

        ExtendingClassDao extendingClassDao = createExtendingClassDao(getNextId());
        extendingClassDao.setParentRoot(result);
        result.getExtendings().add(extendingClassDao);

        result.setFiltering(createSomeFilteringOwnerDaoWithChildren(getNextId()));

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
        result.setExtendedInfo(String.format("SomeName_%d", id));

        return result;
    }

    public static SingleRefOneParentDao createSingleRefOneParentDao(long id) {
        SingleRefOneParentDao result = new SingleRefOneParentDao();
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
        result.setMultiRefs(new ArrayList<>());
        return result;
    }

    public static MultiRefOneParentDao createMultiRefOneParentDaoWithChildren(long id) {
        MultiRefOneParentDao result = createMultiRefOneParentDao(id);
        MultiRefTwoParentsDao multiRefTwoParentsDao = createMultiRefTwoParentsDao(getNextId());
        multiRefTwoParentsDao.setParentMultiRefOneParent(result);
        result.getMultiRefs().add(multiRefTwoParentsDao);
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
        result.setMultiIndirectRefs(new ArrayList<>());
        return result;
    }

    public static MultiRefOtherIndirectParentDao createMultiRefOtherIndirectParentDaoWithChildren(long id, String... identificationsToUse) {
        MultiRefOtherIndirectParentDao result = createMultiRefOtherIndirectParentDao(id);
        for (String identification : identificationsToUse) {
            if (createdDaoObjects.containsKey(identification) && createdDaoObjects.get(identification) instanceof MultiRefIndirectParentDao) {
                MultiRefOtherIndirectParentToMultiRefIndirectParentDao connection = new MultiRefOtherIndirectParentToMultiRefIndirectParentDao();
                connection.setMultiRefOtherIndirectParent(result);
                connection.setMultiRefIndirectParent((MultiRefIndirectParentDao) createdDaoObjects.get(identification));
                result.getMultiIndirectRefs().add(connection);
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
        result.setAggFiltereds(new ArrayList<>());
        result.setAggFilteredOnlyDaoFields(new ArrayList<>());
        return result;
    }

    public static SomeFilteringOwnerDao createSomeFilteringOwnerDaoWithChildren(long id) {
        SomeFilteringOwnerDao result = createSomeFilteringOwnerDao(id);
        result.getAggFiltereds().add(createFilteredDao(getNextId(), AnyEnumType.ENUM_VALUE_A));
        result.getAggFiltereds().add(createFilteredDao(getNextId(), AnyEnumType.ENUM_VALUE_B));
        result.getAggFiltereds().add(createFilteredDao(getNextId(), AnyEnumType.ENUM_VALUE_C));
        result.getAggFilteredOnlyDaoFields().add(createFilteredOnlyDaoFieldDao(getNextId(), AnyEnumType.ENUM_VALUE_A));
        result.getAggFilteredOnlyDaoFields().add(createFilteredOnlyDaoFieldDao(getNextId(), AnyEnumType.ENUM_VALUE_B));
        result.getAggFilteredOnlyDaoFields().add(createFilteredOnlyDaoFieldDao(getNextId(), AnyEnumType.ENUM_VALUE_C));
        return result;
    }

    public static FilteredDao createFilteredDao(long id, AnyEnumType enumType) {
        FilteredDao result = new FilteredDao();
        setId(result, id);
        result.setDescription(String.format("Description_%d", id));
        result.setSomeEnum(enumType);
        return result;
    }

    public static FilteredOnlyDaoFieldDao createFilteredOnlyDaoFieldDao(long id, AnyEnumType enumType) {
        FilteredOnlyDaoFieldDao result = new FilteredOnlyDaoFieldDao();
        setId(result, id);
        result.setDescription(String.format("Description_%d", id));
        result.setSomeEnum(enumType);
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

        result.getMultiRefs().add(createMultiRefOneParentWithChildren(getNextId()));
        result.getAnotherMultiRefs().add(createMultiRefTwoParents(getNextId()));

        result.setSingleRefIndirectParent(createSingleRefIndirectParent(getNextId()));
        addToCreatedMap(result.getSingleRefIndirectParent());
        result.setSingleRefIndirectOtherParent(createSingleRefOtherIndirectParentWithChildren(getNextId()
                , result.getSingleRefIndirectParent().getIdentification()));

        MultiRefIndirectParent multiRefIndirectParent = createMultiRefIndirectParent(getNextId());
        result.getMultiRefIndirectParents().add(multiRefIndirectParent);
        addToCreatedMap(multiRefIndirectParent);
        result.getMultiRefIndirectOtherParents().add(createMultiRefOtherIndirectParentWithChildren(getNextId(), multiRefIndirectParent.getIdentification()));

        result.getExtendings().add(createExtendingClass(getNextId()));

        result.setFiltering(createSomeFilteringOwnerWithChildren(getNextId()));

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
        result.setExtendedInfo(String.format("SomeName_%d", id));

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
        result.getMultiRefs().add(multiRefTwoParents);
        return result;
    }

    public static MultiRefTwoParents createMultiRefTwoParents(long id) {
        MultiRefTwoParents result = new MultiRefTwoParents();
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
                result.addMultiIndirectRefs((MultiRefIndirectParent) createdDomainObjects.get(identification));
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
        result.addFilterAs(createFiltered(getNextId(), AnyEnumType.ENUM_VALUE_A));
        result.addFilterBs(createFiltered(getNextId(), AnyEnumType.ENUM_VALUE_B));
        result.addFilterCs(createFiltered(getNextId(), AnyEnumType.ENUM_VALUE_C));
        result.addFilterDaoAs(createFilteredOnlyDaoField(getNextId()));
        result.addFilterDaoBs(createFilteredOnlyDaoField(getNextId()));
        result.addFilterDaoCs(createFilteredOnlyDaoField(getNextId()));
        return result;
    }

    public static Filtered createFiltered(long id, AnyEnumType enumType) {
        Filtered result = new Filtered();
        setId(result, id, Filtered.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        result.setSomeEnum(enumType);
        return result;
    }

    public static FilteredOnlyDaoField createFilteredOnlyDaoField(long id) {
        FilteredOnlyDaoField result = new FilteredOnlyDaoField();
        setId(result, id, FilteredOnlyDaoField.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static DomainAndDao createDomainAnd(long id) {
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
        result.setExtendedInfo(String.format("SomeName_%d", id));

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

    public static ExtendingClassDto createExtendingClassDto(long id) {
        ExtendingClassDto result = new ExtendingClassDto();
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
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static DomainAndDtoDto createDomainAndDtoDto(long id) {
        DomainAndDtoDto result = new DomainAndDtoDto();
        setId(result, id, DomainAndDto.ID_PREFIX);
        result.setDescription(String.format("Description_%d", id));
        return result;
    }

    public static OnlyDtoDto createOnlyDtoDto(long id) {
        OnlyDtoDto result = new OnlyDtoDto();
        setId(result, id, "ODD");
        result.setDescription(String.format("Description_%d", id));
        return result;
    }
}
